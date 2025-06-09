package com.example.demo.services;

import com.example.demo.model.Weather;

public interface WeatherService {
    Weather getWeatherByCity(String cityName);
}
