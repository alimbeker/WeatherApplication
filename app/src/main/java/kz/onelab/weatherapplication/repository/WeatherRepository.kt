package kz.onelab.weatherapplication.repository

import com.google.gson.Gson
import kz.onelab.weatherapplication.api.WeatherApi
import kz.onelab.weatherapplication.api.WeatherApiError
import kz.onelab.weatherapplication.api.WeatherResponse
import okhttp3.ResponseBody
import javax.inject.Inject


interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): WeatherResponse?

    suspend fun getAllData(city: String): WeatherResponse?
}

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getCurrentWeather(city: String): WeatherResponse? {
        val response = api.getCurrentWeather(city = city, language = "en")
        if (response.isSuccessful) return response.body()
        else throw Exception(response.errorBody().getErrorMessage())
    }

    override suspend fun getAllData(city: String): WeatherResponse? {
        val response = api.getAllData(city = city, language = "en")
        if (response.isSuccessful) return response.body()
        else throw Exception(response.errorBody().getErrorMessage())
    }

}

fun ResponseBody?.getErrorMessage(): String? {
    return try {
        Gson().fromJson(this?.charStream(), WeatherApiError::class.java)?.error?.message
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}