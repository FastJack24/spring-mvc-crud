package com.fastjack24.dao.persondao;

import com.fastjack24.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(
                "SELECT person_id AS id, person_name AS name, age, email FROM person ORDER BY name",
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

    public Person show(int id) {
        return jdbcTemplate.query(
                "SELECT person_id AS id, person_name AS name, age, email FROM person WHERE person_id = ?",
                new BeanPropertyRowMapper<>(Person.class),
                id
        ).stream().findAny().orElse(new Person());
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO person (person_name, age, email) VALUES (?, ?, ?)",
                person.getName(),
                person.getAge(),
                person.getEmail()
        );
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update(
                "UPDATE person SET person_name = ?, age = ?, email = ? WHERE person_id = ?",
                updatedPerson.getName(),
                updatedPerson.getAge(),
                updatedPerson.getEmail(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update(
                "DELETE FROM person WHERE person_id = ?",
                id
        );
    }
}
