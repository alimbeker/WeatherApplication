package kz.onelab.weatherapplication.repository

import com.google.gson.Gson
import kz.onelab.weatherapplication.api.WeatherApi
import kz.onelab.weatherapplication.api.WeatherApiError
import kz.onelab.weatherapplication.api.WeatherResponse
import kz.onelab.weatherapplication.api.WeatherResponseList
import okhttp3.ResponseBody
import javax.inject.Inject


interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): WeatherResponse?

    suspend fun getAllData(city: String): WeatherResponseList?
}

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getCurrentWeather(city: String): WeatherResponse? {
        val response = api.getCurrentWeather(city, "en")
        if (response.isSuccessful) return response.body()
        else throw Exception(response.errorBody().getErrorMessage())
    }

    override suspend fun getAllData(city: String): WeatherResponseList? {
        val call = api.getAllData(city, "en")
        if (call.isSuccessful) return call.body()
        else throw Exception(call.errorBody().getErrorMessage())
    }
}

fun ResponseBody?.getErrorMessage(): String? {
    return try {
        Gson().fromJson(this?.charStream(), WeatherApiError::class.java)?.error?.message
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}