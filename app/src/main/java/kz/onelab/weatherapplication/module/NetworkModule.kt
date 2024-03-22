package kz.onelab.weatherapplication.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.onelab.weatherapplication.data.api.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kz.onelab.weatherapplication.BuildConfig
import kz.onelab.weatherapplication.core.QueryInterceptor

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Client
    // API response interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(QueryInterceptor(hashMapOf("api_key" to BuildConfig.WEATHER_API_KEY)))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun getApi(retrofit: Retrofit): WeatherApi {
        return retrofit
            .create(WeatherApi::class.java)
    }

}