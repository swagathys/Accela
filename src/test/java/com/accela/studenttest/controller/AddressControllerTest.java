package com.accela.studenttest.controller;

import com.accela.studenttest.dao.AddressDAO;
import com.accela.studenttest.dao.PersonDAO;
import com.accela.studenttest.model.Address;
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
public class AddressControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    private Address getAddress(String city, String postalcode, String state, String street, Long id, Person person) {
        Address address = new Address();
        address.setCity(city);
        address.setState(state);
        address.setStreet(street);
        address.setPostal_code(postalcode);
        address.setId(id);
        address.setPerson(person);
        return address;
    }

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
    public void testGetAddress() {
        Person person = getPerson("RAM", "RAJ", 1L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        Address address2 = getAddress("city2", "state2", "pincode2", "street2", 2L, person);
        Address address3 = getAddress("city3", "state3", "pincode3", "street3", 3L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        addressDAO.createAddress(address2);
        addressDAO.createAddress(address3);
        RestAssured.when().get("/rest/api/address").then().statusCode(200).body("size()", is(3));
    }

    @Test
    @Sql("/clean.sql")
    public void testGetAddressByID() {
        Person person = getPerson("RAM", "RAJ", 2L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        Address address2 = getAddress("city2", "state2", "pincode2", "street2", 2L, person);
        Address address3 = getAddress("city3", "state3", "pincode3", "street3", 3L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        addressDAO.createAddress(address2);
        addressDAO.createAddress(address3);
        RestAssured.when().get("/rest/api/address/{id}", 2L).then().statusCode(200).body("city", equalTo("city2"));
    }

    @Test
    @Sql("/clean.sql")
    public void testCreateAddress() {
        Person person = getPerson("RAM", "RAJ", 2L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        personDAO.create(person);
        RestAssured.when().get("/rest/api/address/{id}", 1L).then().statusCode(204);
        given().body(address).contentType("application/json").expect().statusCode(200)
                .when().post("/rest/api/address");
        RestAssured.when().get("/rest/api/address/{id}", 1L).then().statusCode(200).body("city", equalTo("city"));
    }

    @Test
    @Sql("/clean.sql")
    public void testUpdateAddress() {
        Person person = getPerson("RAM", "RAJ", 2L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        RestAssured.when().get("/rest/api/address/{id}", 1L).then().statusCode(200).body("city", equalTo("city"));
        Address addressToUpdate = getAddress("city2", "state2", "pincode2", "street2", 1L, person);
        given().body(addressToUpdate).contentType("application/json").expect().statusCode(200)
                .when().put("/rest/api/address/{id}", 1L);
        RestAssured.when().get("/rest/api/address/{id}", 1L).then().statusCode(200).body("city", equalTo("city2"));
    }

    @Test
    @Sql("/clean.sql")
    public void testDeleteAddress() {
        Person person = getPerson("RAM", "RAJ", 1L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        Address address2 = getAddress("city2", "state2", "pincode2", "street2", 2L, person);
        Address address3 = getAddress("city3", "state3", "pincode3", "street3", 3L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        addressDAO.createAddress(address2);
        addressDAO.createAddress(address3);
        RestAssured.when().get("/rest/api/address").then().statusCode(200).body("size()", is(3));
        RestAssured.when().delete("/rest/api/address/{id}", 1L).then().statusCode(200);
        RestAssured.when().get("/rest/api/address").then().statusCode(200).body("size()", is(2));
    }
}
