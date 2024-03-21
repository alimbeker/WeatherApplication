package kz.onelab.weatherapplication.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location: Location? = null,
    val current: CurrentWeather? = null,
)

data class Location(
    val name: String? = null,//  "Almaty",
    val region: String? = null,//  "Almaty City",
    val country: String? = null,//  "Kazakhstan",
    val lat: Double? = null,//  43.25,
    val lon: Double? = null,//  76.95,
    @SerializedName("tz_id")
    val timeZone: String? = null,//  "Asia/Almaty",
    val localtime: String? = null,//  "2023-11-06 20:57"
)

data class CurrentWeather(
    @SerializedName("last_updated")
    val lastUpdate: String? = null,//  2023-11-06 20:45",
    @SerializedName("temp_c")
    val temp: Double? = null,//  2.0,
    @SerializedName("is_day")
    val isDay: Int? = null,//  0,
    val condition: WeatherCondition? = null,
    @SerializedName("wind_kph")
    val wind: Double? = null,//   6.8,
    @SerializedName("wind_degree")
    val windDegree: Int? = null,//   170,
    @SerializedName("wind_dir")
    val windDirection: String? = null,//  S",
    @SerializedName("pressure_mb")
    val pressure: Double? = null,//  1025.0,
    @SerializedName("precip_mm")
    val precipitation: Double? = null,//   0.0,
    val humidity: Int? = null,//   93,
    val cloud: Int? = null,//   75,
    val uv: Double? = null,//   1.0
)

data class WeatherCondition(
    val text: String? = null,// Дымка",
    val icon: String? = null,// cdn.weatherapi.com/weather/64x64/night/143.png"
)

data class WeatherApiError(
    val error: WeatherApiErrorData? = null
)

data class WeatherApiErrorData(
    val code: Int? = null,
    val message: String? = null
)