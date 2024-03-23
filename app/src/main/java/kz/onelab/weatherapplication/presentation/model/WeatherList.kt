package kz.onelab.weatherapplication.presentation.model

import kz.onelab.weatherapplication.data.model.WeatherCondition

data class WeatherList(
    val name: String? = null,
    val region: String? = null,
    val country: String? = null,
    val temp: Double? = null,
    val wind: Double? = null,
    val condition: WeatherCondition? = null
)