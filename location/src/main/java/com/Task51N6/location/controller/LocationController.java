package com.Task51N6.location.controller;

import com.Task51N6.location.model.Location;
import com.Task51N6.location.model.Weather;
import com.Task51N6.location.repository.GeodataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class LocationController {

    @Autowired
    private GeodataRepository repository;

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("/location")
    public Iterable<Location> findAllLocation() {
        return repository.findAll();
    }

    @GetMapping("/locationName")
    public Optional<Location> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }

    @PostMapping("/location")
    public Location saveLocation(@RequestBody Location geodata) {
        return repository.save(geodata);
    }


    @PutMapping("/location")
    public HttpStatus updateLocation(@RequestParam String name, @RequestBody Location location) {

        Optional<Location> geodata = repository.findByName(name);

        if (geodata.isPresent()) {
            geodata.get().setName(location.getName());
            geodata.get().setLat(location.getLat());
            geodata.get().setLon(location.getLon());
            repository.save(repository.findByName(name).get());
            return HttpStatus.OK;
        }

        return  HttpStatus.BAD_REQUEST;
    }


    @DeleteMapping("/location")
    public HttpStatus deleteLocation(@RequestParam String name) {

        Optional<Location> geodata = repository.findByName(name);

        if (geodata.isPresent()) {
            repository.delete(geodata.get());
            return HttpStatus.OK;
        }

        return  HttpStatus.BAD_REQUEST;
    }


    @GetMapping("/location/weather")
    public Optional<Weather> redirectRequestWeather(@RequestParam String name) {

        Optional<Location> geodata = repository.findByName(name);
        Optional<Weather> resultWeather = Optional.empty();

        if (geodata.isPresent()) {
            String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s",
                    geodata.get().getLat(), geodata.get().getLon());
            resultWeather = Optional.of(restTemplate.getForObject(url, Weather.class));
        }

        return  resultWeather;
    }

}
