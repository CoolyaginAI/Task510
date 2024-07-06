package com.Task51N6.person.repository;

import com.Task51N6.person.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person,Integer> {

}
