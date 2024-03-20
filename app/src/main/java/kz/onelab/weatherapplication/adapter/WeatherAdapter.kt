package kz.onelab.weatherapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.onelab.weatherapplication.R
import kz.onelab.weatherapplication.api.WeatherResponse
import kz.onelab.weatherapplication.databinding.ViewToolbarBinding


class WeatherAdapter :
    ListAdapter<WeatherResponse, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {
    private val weatherList = mutableListOf<WeatherResponse>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ViewToolbarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
    }

    fun addWeather(weather: WeatherResponse) {
        weatherList.add(weather)
        submitList(weatherList)
    }

    inner class WeatherViewHolder(private val binding: ViewToolbarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherResponse) {
            binding.apply {
                location.text = "${weather?.location?.name}, ${weather?.location?.country}"
                temperature.text = "${weather.current?.temp}°"
                wind.text = "Wind: ${weather?.current?.wind} km/h"
                daytime.text = "${if (weather?.current?.isDay == 1) "day" else "night"}"
                weatherCondition.text = weather?.current?.condition?.text
                Glide.with(binding.root.context)
                    .load("https:${weather?.current?.condition?.icon}")
                    .into(weatherConditionIcon)
            }

        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherResponse>() {
    override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
        return oldItem.location?.name == newItem.location?.name
    }

    override fun areContentsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
        return oldItem == newItem
    }
}

