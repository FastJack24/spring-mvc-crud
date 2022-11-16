package com.fastjack24.dao.persondao;

import com.fastjack24.model.Person;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public boolean emailCheck(String email) {
        return jdbcTemplate.query(
                "SELECT person_id as id, person_name AS name, age, email FROM person WHERE email = ?",
                new BeanPropertyRowMapper<>(Person.class),
                email
        ).stream().findAny().isPresent();
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
    // It's better to use batch to perform multiple operations
    /* Testing batch */
    public void testMultipleUpdate() { // Many updates
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update(
                    "INSERT INTO person (person_name, age, email) VALUES(?, ?, ?)",
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate() { // Many updates in one batch
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate(
                "INSERT INTO person (person_name, age, email) VALUES(?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setInt(   2, people.get(i).getAge());
                        ps.setString(3, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++)
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@mail.ru"));

        return people;
    }
}
