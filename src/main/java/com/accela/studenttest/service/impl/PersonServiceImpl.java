package com.accela.studenttest.service.impl;

import com.accela.studenttest.dao.PersonDAO;
import com.accela.studenttest.model.Person;
import com.accela.studenttest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDAO personDAO;

    @Override
    public String addPerson(Person person) {
        boolean result = personDAO.create(person);
        return result ? "Successfully added new Person" : "Failed to add new Person";
    }

    @Override
    public String updatePerson(Long id, Person person) {
        boolean result = personDAO.update(id, person);
        return result ? "Successfully updated Person" : "Failed to update Person";
    }

    @Override
    public String deletePerson(Long id) {
        boolean result = personDAO.delete(id);
        return result ? "Successfully deleted" : "Failed to delete {}" + id;
    }

    @Override
    public Person findPerson(Long id) {
        return personDAO.getPersonById(id);
    }

    @Override
    public List<Person> findAllPersons() {
        return personDAO.getAllPersons();
    }
}
