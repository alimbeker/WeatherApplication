package kz.onelab.weatherapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.weatherapplication.databinding.FragmentWeatherInfoBinding
import java.text.SimpleDateFormat

@AndroidEntryPoint
class WeatherInfoFragment : Fragment() {
    private lateinit var binding: FragmentWeatherInfoBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherInfoBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.current.setOnClickListener {
            if (!binding.cityInput.text.isNullOrBlank()) {
                binding.weatherBox.isVisible = false
                binding.root.hideKeyboard()
                viewModel.getCurrentWeather(binding.cityInput.text.toString())
            }
            else Toast.makeText(this.context, "Input city name", Toast.LENGTH_SHORT).show()
        }
        setUpData()
        setUpLoader()
        setUpError()

        return binding.root
    }


    private fun setUpError() {
        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpLoader() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpData() {
        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                weatherBox.isVisible = it != null

                city.text = "City: ${it?.location?.name}"
                region.text = "Region: ${it?.location?.region}"
                contry.text = "Country: ${it?.location?.country}"
                coors.text = "Coors: ${it?.location?.lat}, ${it?.location?.lon}"
                timeZone.text = "Time zone: ${it?.location?.timeZone}"
                localTime.text = "Time: ${it?.location?.localtime?.getDateTime()}"

                lastUpdate.text = "Last update: ${it?.current?.lastUpdate?.getDateTime()}"
                temp.text = "Temperature: ${it?.current?.temp}"
                isDay.text = "Is day: ${if (it?.current?.isDay == 1) "true" else "false"}"
                wind.text = "Wind: ${it?.current?.wind}"
                windDegree.text = "Wind degree: ${it?.current?.windDegree}"
                windDirection.text = "Wind direction: ${it?.current?.windDirection}"
                conditionText.text = it?.current?.condition?.text

                Glide.with(this@WeatherInfoFragment)
                    .load("https:${it?.current?.condition?.icon}")
                    .into(conditionIcon)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun String.getDateTime(): String? {
        val fromFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val toFormatter = SimpleDateFormat("HH:mm, dd MMMM yyy")
        return fromFormatter.parse(this)?.let {
            toFormatter.format(it)
        }
    }

    private fun View.hideKeyboard() {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }

}

