package com.fastjack.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Person {
    // Most web apps are CRUD apps (Database access with HTTP protocol)
    // CRUD (CREATE, READ, UPDATE, DELETE) application:
    // Standard data manipulation functions
    // We also use conventions for REST (a design pattern for WEB), what URLs to use and how!!!

    // We also don't want to access database from entity class,
    // there we MUST have DAO pattern used (Data Access Object)
    // This way we encapsulate the interaction between post model and database to have more flexible development
    // DAO usually has SQL code for interaction with DB, but we also have a REPOSITORY PATTERN
    // that allows to interact with DB with the help of Hibernate and Spring Data JPA,
    // there we'll have more abstraction with DB than with DAO

    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 32, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "\\p{javaUpperCase}.*", message = "Name should start with upper case")
    private String name;

    @Min(value = 0, message = "Age should be grater than 0.")
    @Max(value = 150, message = "Age should be lower than 150")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
