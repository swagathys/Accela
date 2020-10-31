package com.accela.studenttest.service;

import com.accela.studenttest.model.Person;

import java.util.List;

public interface PersonService {

    String addPerson(Person person);

    String updatePerson(Long id, Person person);

    String deletePerson(Long id);

    Person findPerson(Long id);

    List<Person> findAllPersons();
}
