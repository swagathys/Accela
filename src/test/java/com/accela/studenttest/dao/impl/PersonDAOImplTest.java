package com.accela.studenttest.dao.impl;

import com.accela.studenttest.dao.PersonDAO;
import com.accela.studenttest.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonDAOImplTest {

    @Autowired
    private PersonDAO personDAO;

    private Person getPerson(String firstName, String lastName, Long id) {
        Person person = new Person();
        person = new Person();
        person.setLastName(firstName);
        person.setFirstName(lastName);
        person.setId(id);
        return person;
    }

    @Test
    @Sql("/clean.sql")
    public void testAddPerson() {
        Person person = personDAO.getPersonById(123L);
        Assert.assertNull(person);
        Person person1 = getPerson("Ram", "Raj", 123L);
        personDAO.create(person1);
        person = personDAO.getPersonById(123L);
        Assert.assertNotNull(person);
    }

    @Test
    @Sql("/clean.sql")
    public void testFindById() {
        Person person = personDAO.getPersonById(123L);
        Assert.assertNull(person);
        Person person1 = getPerson("Ram", "Raj", 123L);
        personDAO.create(person1);
        person = personDAO.getPersonById(123L);
        Assert.assertNotNull(person);
        Assert.assertEquals(person1.getFirstName(), person.getFirstName());
        Assert.assertEquals(person1.getLastName(), person.getLastName());
    }

    @Test
    @Sql("/clean.sql")
    public void testFindAllPersons() {
        List<Person> allPersons = personDAO.getAllPersons();
        Assert.assertEquals(0, allPersons.size());
        Person person1 = getPerson("Ram", "Raj", 2L);
        Person person2 = getPerson("Dev", "Man", 3L);
        Person person3 = getPerson("Jam", "Fan", 4L);
        personDAO.create(person1);
        personDAO.create(person2);
        personDAO.create(person3);
        allPersons = personDAO.getAllPersons();
        Assert.assertEquals(3, allPersons.size());
    }


    @Test
    @Sql("/clean.sql")
    public void testUpdatePerson() {
        Person person = personDAO.getPersonById(1L);
        Assert.assertNull(person);
        Person person1 = getPerson("Ram", "Raj", 1L);
        personDAO.create(person1);
        person = personDAO.getPersonById(1L);
        Assert.assertNotNull(person);
        Assert.assertEquals(person1.getFirstName(), person.getFirstName());
        Assert.assertEquals(person1.getLastName(), person.getLastName());
        Person personToUpdate = getPerson("Man", "Ram", 1L);
        personDAO.update(1L, personToUpdate);
        person = personDAO.getPersonById(1L);
        Assert.assertNotNull(person);
        Assert.assertEquals(personToUpdate.getFirstName(), person.getFirstName());
        Assert.assertEquals(personToUpdate.getLastName(), person.getLastName());
    }
}