package com.Task51N6.weather.controller;

import com.Task51N6.weather.model.Main;
import com.Task51N6.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RestController
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appId;

    @Value("${url.weather}")
    private String urlWeather;

    @Cacheable("weather")
    @GetMapping("/weather")
    public Optional<Main> getWeather(@RequestParam String lat, @RequestParam String lon) {
        String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);
        return  Optional.of(restTemplate.getForObject(request, Root.class).getMain());
    }

}
