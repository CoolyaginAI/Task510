package com.Task51N6.location.repository;

import com.Task51N6.location.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeodataRepository extends CrudRepository<Location,Integer> {
    Optional<Location> findByName(String name);
}
