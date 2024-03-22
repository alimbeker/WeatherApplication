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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {


    private var _currentWeatherLiveData = MutableLiveData<Resource<WeatherInfo?>>()
    val currentWeatherLiveData: LiveData<Resource<WeatherInfo?>> = _currentWeatherLiveData

    private var _weatherListLiveData = MutableLiveData<WeatherResponse?>()
    val weatherListLiveData: LiveData<WeatherResponse?> = _weatherListLiveData
//    fun getCurrentWeather(city: String) {
//        launch(request = {
//            repository.getCurrentWeather(city)
//        }, onSuccess = {
//            _currentWeatherLiveData.postValue(it)
//        })
//    }

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


}

abstract class BaseViewModel : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private var _exceptionLiveData = MutableLiveData<String?>()
    val exceptionLiveData: LiveData<String?> = _exceptionLiveData

    fun <T> launch(
        request: suspend () -> T, onSuccess: (T) -> Unit = { }
    ) {
        coroutineScope.launch {
            try {
                _loadingLiveData.postValue(true)
                val response = request.invoke()
                onSuccess.invoke(response)
            } catch (e: Exception) {
                _exceptionLiveData.postValue(e.message)
                Log.e(">>>", e.message.orEmpty())
            } finally {
                _loadingLiveData.postValue(false)
            }
        }
    }
}