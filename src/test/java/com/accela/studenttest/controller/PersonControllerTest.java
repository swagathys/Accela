package com.accela.studenttest.controller;

import com.accela.studenttest.dao.PersonDAO;
import com.accela.studenttest.model.Person;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private PersonDAO personDAO;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    private Person getPerson(String firstName, String lastName, Long id) {
        Person person = new Person();
        person = new Person();
        person.setLastName(lastName);
        person.setFirstName(firstName);
        person.setId(id);
        return person;
    }

    @Test
    @Sql("/clean.sql")
    public void getAllPersons() {
        Person person1 = getPerson("Ram", "Raj", 1L);
        Person person2 = getPerson("Ram", "Raj", 2L);
        personDAO.create(person1);
        personDAO.create(person2);
        RestAssured.when().get("/rest/api/person").then().statusCode(200).body("size()", is(2));
    }

    @Test
    @Sql("/clean.sql")
    public void getPersonById() {
        Person person1 = getPerson("Ram", "Raj", 1L);
        Person person2 = getPerson("Ram", "Raj", 2L);
        personDAO.create(person1);
        personDAO.create(person2);
        RestAssured.when().get("/rest/api/person/{id}", 1L).then().statusCode(200).body("id", equalTo(1));
    }

    @Test
    @Sql("/clean.sql")
    public void testUpdatePerson() {
        Person person1 = getPerson("Ram", "Raj", 1L);
        Person person2 = getPerson("Sam", "dev", 1L);
        personDAO.create(person1);
        RestAssured.when().get("/rest/api/person/{id}", 1L).then().statusCode(200).body("firstName", equalTo("Ram"));
        given().body(person2).contentType("application/json").expect().statusCode(200)
                .when().put("/rest/api/person/{id}", 1L);
        RestAssured.when().get("/rest/api/person/{id}", 1L).then().statusCode(200).body("firstName", equalTo("Sam"));
    }

    @Test
    @Sql("/clean.sql")
    public void testCreatePerson() {
        Person person1 = getPerson("Ram", "Raj", 1L);
        RestAssured.when().get("/rest/api/person/{id}", 1L).then().statusCode(204);
        given().body(person1).contentType("application/json").expect().statusCode(200)
                .when().post("/rest/api/person");
        RestAssured.when().get("/rest/api/person/{id}", 1L).then().statusCode(200).body("firstName", equalTo("Ram"));
    }

    @Test
    @Sql("/clean.sql")
    public void testDeletePerson() {
        Person person1 = getPerson("Ram", "Raj", 1L);
        Person person2 = getPerson("Sam", "dev", 2L);
        personDAO.create(person1);
        personDAO.create(person2);
        RestAssured.when().get("/rest/api/person").then().statusCode(200).body("size()", is(2));
        RestAssured.when().delete("/rest/api/person/{id}", 1L).then().statusCode(200);
        RestAssured.when().get("/rest/api/person").then().statusCode(200).body("size()", is(1));
    }
}