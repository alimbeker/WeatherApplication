package kz.onelab.weatherapplication.data.repository

import kz.onelab.weatherapplication.data.model.WeatherResponse
import kz.onelab.weatherapplication.presentation.model.WeatherInfo

// здесь мапишь нужные поля в WeatherInfo
fun WeatherResponse.toPresentation(): WeatherInfo = WeatherInfo(
    name = location?.name,
    region = location?.region,
    country = location?.country,
    lat = location?.lat,
    lon = location?.lon,
    timeZone = location?.timeZone,
    localtime = location?.localtime,
    lastUpdate = current?.lastUpdate,
    temp = current?.temp,
    isDay = current?.isDay,
    wind = current?.wind,
    windDegree = current?.windDegree,
    windDirection = current?.windDirection,
    condition = current?.condition,
)

