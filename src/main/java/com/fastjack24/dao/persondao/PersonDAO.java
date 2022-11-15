package com.fastjack24.dao.persondao;

import com.fastjack24.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static final String url = "jdbc:postgresql://localhost:5432/webappmvc";
    private static final String username = "postgres";
    private static final String password = "password";
    private static final Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver is not found", e);
        }

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to load drivers", e);
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(sql); // Returns data, changes nothing

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("person_id"));
                person.setName(resultSet.getString("person_name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to list all users", e); // Exception handling
        }
        return people;
    }

    public Person show(int id) {
        Person person;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM person where person_id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("person_id"));
            person.setName(resultSet.getString("person_name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to access user data", e);
        }
        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO person (person_name, age, email) VALUES (?, ?, ?);"
            );
            // NEVER!!! concat SQL when you get data from user
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(   2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            // Only in places! and this statement exactly.
            // Prepared statement compiles only once!,
            // statement every time you use it, prepared can be cashed by db too

            preparedStatement.executeUpdate(); // Changes data, returns nothing
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to save a person", e);
        }
    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE person SET person_name = ?, age = ?, email = ? WHERE person_id = ?"
            );
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(   2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(   4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to update the person", e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM person WHERE person_id = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to delete the person", e);
        }
    }
}
