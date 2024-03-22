package kz.onelab.weatherapplication.presentation.model

import com.google.gson.annotations.SerializedName
import kz.onelab.weatherapplication.data.model.WeatherCondition

// добавь нужные для показа поля
//сделано
data class WeatherInfo(
    val name: String? = null,//  "Almaty",
    val region: String? = null,//  "Almaty City",
    val country: String? = null,//  "Kazakhstan",
    val lat: Double? = null,//  43.25,
    val lon: Double? = null,//  76.95,
    @SerializedName("tz_id")
    val timeZone: String? = null,//  "Asia/Almaty",
    val localtime: String? = null,//  "2023-11-06 20:57"

    @SerializedName("last_updated")
    val lastUpdate: String? = null,//  2023-11-06 20:45",
    @SerializedName("temp_c")
    val temp: Double? = null,//  2.0,
    @SerializedName("is_day")
    val isDay: Int? = null,//  0,
    @SerializedName("wind_kph")
    val wind: Double? = null,//   6.8,
    @SerializedName("wind_degree")
    val windDegree: Int? = null,//   170,
    @SerializedName("wind_dir")
    val windDirection: String? = null,//  S",
    val condition: WeatherCondition? = null,
)


