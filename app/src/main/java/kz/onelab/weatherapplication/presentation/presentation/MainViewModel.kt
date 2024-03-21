package kz.onelab.weatherapplication.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.onelab.weatherapplication.data.model.WeatherResponse
import kz.onelab.weatherapplication.data.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : BaseViewModel() {


    private var _currentWeatherLiveData = MutableLiveData<WeatherResponse?>()
    val currentWeatherLiveData: LiveData<WeatherResponse?> = _currentWeatherLiveData

    private var _weatherListLiveData = MutableLiveData<WeatherResponse?>()
    val weatherListLiveData: LiveData<WeatherResponse?> = _weatherListLiveData
    fun getCurrentWeather(city: String) {
        launch(request = {
            repository.getCurrentWeather(city)
        }, onSuccess = {
            _currentWeatherLiveData.postValue(it)
        })
    }

    fun getAllData(city: String) {
        launch(request = {
            repository.getAllData(city)
        }, onSuccess = {
            _weatherListLiveData.postValue(it)
        })
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