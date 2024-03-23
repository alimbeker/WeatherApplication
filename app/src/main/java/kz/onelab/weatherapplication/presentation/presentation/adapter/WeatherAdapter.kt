package kz.onelab.weatherapplication.presentation.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.onelab.weatherapplication.databinding.ViewToolbarBinding
import kz.onelab.weatherapplication.presentation.model.WeatherList


class WeatherAdapter :
    ListAdapter<WeatherList, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ViewToolbarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
    }

    inner class WeatherViewHolder(private val binding: ViewToolbarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weather: WeatherList) {
            binding.apply {
                location.text = "${weather.name}, ${weather.country}"
                temperature.text = "${weather.temp}°"
                wind.text = "Wind: ${weather.wind} km/h"
                weatherCondition.text = weather.condition?.text
                Glide.with(binding.root.context)
                    .load("https:${weather.condition?.icon}")
                    .into(weatherConditionIcon)

            }
        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherList>() {
    override fun areItemsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem == newItem
    }
}

