package kz.onelab.weatherapplication.data.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.onelab.weatherapplication.core.BaseRepository
import kz.onelab.weatherapplication.core.functional.State
import kz.onelab.weatherapplication.data.api.WeatherApi
import kz.onelab.weatherapplication.data.repository.toAdapter
import kz.onelab.weatherapplication.data.repository.toPresentation
import kz.onelab.weatherapplication.presentation.model.WeatherInfo
import kz.onelab.weatherapplication.presentation.model.WeatherList
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) : BaseRepository() {

    //посмотри примеры прошлого урока (5 урок)
    //если пример с бд с кэшированием то 8 урок
    suspend fun getCurrentWeather(city: String): State<Throwable, WeatherInfo> = apiCall {
        withContext(Dispatchers.IO) {
            api.getCurrentWeather(city = city, language = "en").toPresentation()
        }
    }

    suspend fun getAllData(city: String): State<Throwable, WeatherList> = apiCall {
        withContext(Dispatchers.IO) {
            api.getAllData(city = city, language = "en").toAdapter()
        }
    }
}
