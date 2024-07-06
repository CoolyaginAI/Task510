package com.Task51N6.person.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id @GeneratedValue
    private int id;

    @NonNull String firstname;
    @NonNull String surname;
    @NonNull String lastname;
    @NonNull LocalDate birthday;
    @NonNull private String location;

    public Person(@NonNull String firstname,
                  @NonNull String surname,
                  @NonNull String lastname,
                  @NonNull LocalDate birthday,
                  @NonNull String location) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.location = location;
    }

}
