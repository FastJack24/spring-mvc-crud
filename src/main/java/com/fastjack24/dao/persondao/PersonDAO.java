package com.fastjack24.dao.persondao;

import com.fastjack24.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;
    private List<Person> people; // Database for now.

    public PersonDAO() {
        people = new ArrayList<>(); // Creating database.
        people.add(new Person(++PEOPLE_COUNT, "Andrew", 23, "foxs@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Tom", 38, "tms@ex.org"));
        people.add(new Person(++PEOPLE_COUNT, "Jack",54, "jack@mail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        for (Person person: people) {
            if (person.getId() == id) { return person; }
        }
        return null;
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person newPerson) {
        Person personToBeUpdated = this.show(id);
        personToBeUpdated.setName(newPerson.getName());
        personToBeUpdated.setAge(newPerson.getAge());
        personToBeUpdated.setEmail(newPerson.getEmail());
    }

    public void delete(int id) {
        people.remove(this.show(id));
    }
}
