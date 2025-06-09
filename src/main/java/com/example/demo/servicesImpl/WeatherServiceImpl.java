package com.example.demo.servicesImpl;

import com.example.demo.model.Weather;
import com.example.demo.services.WeatherService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {
    
    private final RestTemplate restTemplate;
    
    @Value("${weather.api.key}")
    private String apiKey;
    
    @Value("${weather.api.url}")
    private String weatherBaseUrl;
    
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
      @Override
    public Weather getWeatherByCity(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            System.out.println("Nombre de ciudad vacío para consulta del clima");
            return null;
        }
          try {
            String fullUrl = weatherBaseUrl + "?q={city}&appid=" + apiKey + "&units=metric&lang=es";
            System.out.println("Obteniendo datos del clima para la ciudad: " + cityName);
            Weather weather = restTemplate.getForObject(fullUrl, Weather.class, cityName.trim());
            
            if (weather != null) {
                System.out.println("Datos del clima obtenidos exitosamente para: " + cityName);
            } else {
                System.out.println("No se encontraron datos del clima para: " + cityName);
            }
            
            return weather;
            
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Ciudad no encontrada en OpenWeather API: " + cityName);
            return null;
        } catch (HttpClientErrorException e) {
            System.err.println("Error de cliente al obtener datos del clima para '" + cityName + "': " + e.getMessage());
            return null;
        } catch (ResourceAccessException e) {
            System.err.println("Error de red al obtener datos del clima para '" + cityName + "': " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener datos del clima para '" + cityName + "': " + e.getMessage());
            return null;
        }
    }
}
