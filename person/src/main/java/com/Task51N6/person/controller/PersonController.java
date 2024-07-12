package com.Task51N6.person.controller;


import com.Task51N6.person.model.Main;
import com.Task51N6.person.model.Person;
import com.Task51N6.person.model.Weather;
import com.Task51N6.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    public RestTemplate restTemplate;

    @Value("${url.location}")
    String urlLocation;

    @GetMapping("/person")
    public Iterable<Person> findAllPerson() {
        return personRepository.findAll();
    }

    @GetMapping("/person/{id}")
    public Optional<Person> findPersonById(@PathVariable int id) {
        return  personRepository.findById(id);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        return  personRepository.findById(person.getId()).isPresent()
                ? new ResponseEntity(personRepository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(personRepository.save(person), HttpStatus.CREATED);
    }


    @PutMapping("/person/{id}")
    public HttpStatus updatePerson(@PathVariable int id, @RequestBody Person person) {

        Optional<Person> tPerson = personRepository.findById(id);

        if (tPerson.isPresent()) {
            tPerson.get().setFirstname(person.getFirstname());
            tPerson.get().setSurname(person.getSurname());
            tPerson.get().setLastname(person.getLastname());
            tPerson.get().setBirthday(person.getBirthday());
            tPerson.get().setLocation(person.getLocation());
            personRepository.save(personRepository.findById(id).get());
            return HttpStatus.OK;
        }

        return  HttpStatus.BAD_REQUEST;
    }


    @DeleteMapping("/person/{id}")
    public HttpStatus deletePerson(@PathVariable int id) {

        Optional<Person> tPerson = personRepository.findById(id);

        if (tPerson.isPresent()) {
            personRepository.delete(tPerson.get());
            return HttpStatus.OK;
        }
        return  HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/person/{id}/weather")
    public ResponseEntity<Main> getWeatherPerson(@PathVariable int id) {
        if (personRepository.existsById(id)) {
            String location = personRepository.findById(id).get().getLocation();
            String request = String.format("http://%s/location/weather?name=%s", urlLocation, location);
            Main weatherMain = restTemplate.getForObject(request, Main.class);
            return new ResponseEntity(weatherMain, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }


}
