package com.accela.studenttest.dao;

import com.accela.studenttest.model.Person;

import java.util.List;

public interface PersonDAO {

    boolean create(Person person);

    boolean update(Long id, Person person);

    Person getPersonById(long id);

    List<Person> getAllPersons();

    boolean delete(long id);
}
