package kz.onelab.weatherapplication.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.onelab.weatherapplication.data.api.WeatherApi
import kz.onelab.weatherapplication.data.repository.WeatherRepository
import kz.onelab.weatherapplication.data.repository.WeatherRepositoryImpl

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository = WeatherRepositoryImpl(api)
}