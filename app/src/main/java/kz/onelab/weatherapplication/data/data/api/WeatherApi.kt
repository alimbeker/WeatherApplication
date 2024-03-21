package kz.onelab.weatherapplication.data.api

import kz.onelab.weatherapplication.data.model.WeatherResponse
import kz.onelab.weatherapplication.module.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    /**
     * Убери response, Возвращай WeatherResponse
     * посмотри примеры прошлого урока (5 урок)
     */
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String = NetworkModule.API_KEY,
        @Query("q") city: String,
        @Query("lang") language: String,
    ): Response<WeatherResponse>
    @GET("current.json")
    suspend fun getAllData(
        @Query("key") key: String = NetworkModule.API_KEY,
        @Query("q") city: String,
        @Query("lang") language: String,
    ): Response<WeatherResponse>

}