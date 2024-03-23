package kz.onelab.weatherapplication.presentation.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.onelab.weatherapplication.core.functional.Resource
import kz.onelab.weatherapplication.core.functional.onFailure
import kz.onelab.weatherapplication.core.functional.onSuccess
import kz.onelab.weatherapplication.data.model.WeatherResponse
import kz.onelab.weatherapplication.data.data.repository.WeatherRepository
import kz.onelab.weatherapplication.presentation.model.WeatherInfo
import kz.onelab.weatherapplication.presentation.model.WeatherList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {


    private var _currentWeatherLiveData = MutableLiveData<Resource<WeatherInfo?>>()
    val currentWeatherLiveData: LiveData<Resource<WeatherInfo?>> = _currentWeatherLiveData

    private var _weatherListLiveData = MutableLiveData<Resource<WeatherList?>>()
    val weatherListLiveData: LiveData<Resource<WeatherList?>> = _weatherListLiveData

    fun getCurrentWeather(city: String) {
        _currentWeatherLiveData.value = Resource.Loading
        viewModelScope.launch {
            repository.getCurrentWeather(city)
                .onFailure { throwable ->
                    _currentWeatherLiveData.value = Resource.Error(throwable)
                }
                .onSuccess { weatherData ->
                    _currentWeatherLiveData.value = Resource.Success(weatherData)
                }
        }
    }

    fun getAllData(city: String) {
        _weatherListLiveData.value = Resource.Loading
        viewModelScope.launch {
            repository.getAllData(city)
                .onFailure { throwable ->
                    _weatherListLiveData.value = Resource.Error(throwable)
                }
                .onSuccess { weatherData ->
                    _weatherListLiveData.value = Resource.Success(weatherData)
                }
        }
    }
}

