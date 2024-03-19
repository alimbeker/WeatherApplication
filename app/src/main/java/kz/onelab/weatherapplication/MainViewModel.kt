package kz.onelab.weatherapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.onelab.weatherapplication.api.WeatherResponse
import kz.onelab.weatherapplication.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : BaseViewModel(

) {


    private var _currentWeatherLiveData = MutableLiveData<WeatherResponse?>()
    val currentWeatherLiveData: LiveData<WeatherResponse?> = _currentWeatherLiveData

    private val weatherList = ArrayList<WeatherResponse?>()
    private var _weatherListLiveData = MutableLiveData<List<WeatherResponse?>>()
    val weatherListLiveData: LiveData<List<WeatherResponse?>> = _weatherListLiveData
    fun getCurrentWeather(city: String) {
        launch(request = {
            repository.getCurrentWeather(city)
        }, onSuccess = {
            _currentWeatherLiveData.postValue(it)
            weatherList.add(it)
            _weatherListLiveData.postValue(weatherList)
            Log.d(">>>>>>>", "Initial weatherList: $weatherList")
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