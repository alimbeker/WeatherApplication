package kz.onelab.weatherapplication.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.onelab.weatherapplication.data.api.WeatherApi
import kz.onelab.weatherapplication.data.data.repository.WeatherRepository

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository = WeatherRepository(api)
}