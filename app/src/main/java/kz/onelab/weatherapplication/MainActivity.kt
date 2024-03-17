package kz.onelab.weatherapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import androidx.activity.viewModels
import kz.onelab.weatherapplication.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.current.setOnClickListener {
            if (!binding.cityInput.text.isNullOrBlank()) {
                binding.weatherBox.isVisible = false
                binding.root.hideKeyboard()
                viewModel.getCurrentWeather(binding.cityInput.text.toString())
            }
            else Toast.makeText(this, "Input city name", Toast.LENGTH_SHORT).show()
        }
        setUpData()
        setUpLoader()
        setUpError()
    }

    private fun setUpError() {
        viewModel.exceptionLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpLoader() {
        viewModel.loadingLiveData.observe(this) {
            binding.loading.isVisible = it
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpData() {
        viewModel.currentWeatherLiveData.observe(this) {
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

                Glide.with(this@MainActivity)
                    .load("https:${it?.current?.condition?.icon}")
                    .into(conditionIcon)
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun String.getDateTime(): String? {
    val fromFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val toFormatter = SimpleDateFormat("HH:mm, dd MMMM yyy")
    return fromFormatter.parse(this)?.let {
        toFormatter.format(it)
    }
}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}