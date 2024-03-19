package kz.onelab.weatherapplication.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json?key=9756abb3465d414db22182052241903")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("lang") language: String
    ): Response<WeatherResponse>
    @GET("current.json?key=bf4dc83bbe114579817143928230611")
    fun getAllData(
        @Query("q") city: String,
        @Query("lang") language: String
    ): Response<WeatherResponseList>

}