package kz.onelab.weatherapplication.presentation.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import androidx.navigation.fragment.findNavController
import kz.onelab.weatherapplication.core.base.BaseFragment
import kz.onelab.weatherapplication.core.functional.Resource
import kz.onelab.weatherapplication.databinding.FragmentWeatherInfoBinding

@AndroidEntryPoint
class WeatherInfoFragment : BaseFragment<FragmentWeatherInfoBinding>(FragmentWeatherInfoBinding::inflate) {
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.current.setOnClickListener {
            if (!binding.cityInput.text.isNullOrBlank()) {
                binding.weatherBox.isVisible = false
                binding.root.hideKeyboard()

                viewModel.getCurrentWeather(binding.cityInput.text.toString())
            } else Toast.makeText(this.context, "Input city name", Toast.LENGTH_SHORT).show()
        }

        binding.navigate.setOnClickListener {
            findNavController().navigate(
                WeatherInfoFragmentDirections.actionWeatherInfoFragmentToWeatherListFragment(binding.cityInput.text.toString())
            )
        }
        setUpData()

    }


    @SuppressLint("SetTextI18n")
    private fun setUpData() {
        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { resource ->

            when (resource) {
                is Resource.Loading -> {
                    binding.loading.isVisible = true
                }

                is Resource.Success -> {
                    binding.loading.isVisible = false

                    val weatherInfo = resource.data
                    with(binding) {
                        weatherBox.isVisible = weatherInfo != null

                        city.text = "City: ${weatherInfo?.name}"
                        region.text = "Region: ${weatherInfo?.region}"
                        country.text = "Country: ${weatherInfo?.country}"
                        coords.text = "Coords: ${weatherInfo?.lat}, ${weatherInfo?.lon}"
                        timeZone.text = "Time zone: ${weatherInfo?.timeZone}"
                        localTime.text = "Time: ${weatherInfo?.localtime?.getDateTime()}"

                        lastUpdate.text = "Last update: ${weatherInfo?.lastUpdate?.getDateTime()}"
                        temp.text = "Temperature: ${weatherInfo?.temp}"
                        isDay.text = "Is day: ${if (weatherInfo?.isDay == 1) "true" else "false"}"
                        wind.text = "Wind: ${weatherInfo?.wind}"
                        windDegree.text = "Wind degree: ${weatherInfo?.windDegree}"
                        windDirection.text = "Wind direction: ${weatherInfo?.windDirection}"
                        conditionText.text = weatherInfo?.condition?.text

                        Glide.with(this@WeatherInfoFragment)
                            .load("https:${weatherInfo?.condition?.icon}")
                            .into(conditionIcon)
                    }
                }

                is Resource.Error -> {
                    binding.loading.isVisible = false

                    Toast.makeText(this.context, resource.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
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

