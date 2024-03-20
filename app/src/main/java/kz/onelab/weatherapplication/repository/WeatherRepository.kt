package kz.onelab.weatherapplication.repository

import com.google.gson.Gson
import kz.onelab.weatherapplication.api.WeatherApi
import kz.onelab.weatherapplication.api.WeatherApiError
import kz.onelab.weatherapplication.api.WeatherResponse
import kz.onelab.weatherapplication.api.WeatherResponseList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    override suspend fun getAllData(city: String, language: String, callback: (WeatherResponseList?) -> Unit) {
        val call = api.getAllData(city, "en")
        call.enqueue(object : Callback<WeatherResponseList> {
            override fun onResponse(call: Call<WeatherResponseList>, response: Response<WeatherResponseList>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    callback(data)
                } else {
                    // Обработка неудачного ответа, если необходимо
                    // Возможно, здесь вы захотите передать null или обработать иным способом
                    callback(null)
                }
            }

            override fun onFailure(call: Call<WeatherResponseList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}

fun ResponseBody?.getErrorMessage(): String? {
    return try {
        Gson().fromJson(this?.charStream(), WeatherApiError::class.java)?.error?.message
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}