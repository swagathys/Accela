package com.accela.studenttest.dao.impl;

import com.accela.studenttest.dao.AddressDAO;
import com.accela.studenttest.dao.PersonDAO;
import com.accela.studenttest.model.Address;
import com.accela.studenttest.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AddressDAOImplTest {

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private PersonDAO personDAO;

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
    public void testAddAddress() {
        Person person = getPerson("RAM", "RAJ", 1L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        Address addressById = addressDAO.getAddressById(1L);
        Assert.assertNull(addressById);
        personDAO.create(person);
        addressDAO.createAddress(address);
        addressById = addressDAO.getAddressById(1L);
        Assert.assertNotNull(addressById);
    }

    @Test
    @Sql("/clean.sql")
    public void testUpdateAddress() {
        Person person = getPerson("RAM", "RAJ", 1L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        Address addressById = addressDAO.getAddressById(1L);
        Assert.assertNotNull(addressById);
        Assert.assertEquals(address.getCity(), addressById.getCity());
        Assert.assertEquals(address.getState(), addressById.getState());
        Assert.assertEquals(address.getPostal_code(), addressById.getPostal_code());
        Assert.assertEquals(address.getStreet(), addressById.getStreet());
        Address addressToUpdate = getAddress("dub", "lin", "d15", "river", 1L, person);
        addressDAO.updateAddress(1L, addressToUpdate);
        addressById = addressDAO.getAddressById(1L);
        Assert.assertNotNull(addressById);
        Assert.assertEquals(addressToUpdate.getCity(), addressById.getCity());
        Assert.assertEquals(addressToUpdate.getState(), addressById.getState());
        Assert.assertEquals(addressToUpdate.getPostal_code(), addressById.getPostal_code());
        Assert.assertEquals(addressToUpdate.getStreet(), addressById.getStreet());
    }

    @Test
    @Sql("/clean.sql")
    public void testDeleteAddress() {
        Person person = getPerson("RAM", "RAJ", 1L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        Address address2 = getAddress("city2", "state2", "pincode2", "street2", 2L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        addressDAO.createAddress(address2);
        List<Address> addresses = addressDAO.getAllAddress();
        Assert.assertEquals(2, addresses.size());
        addressDAO.delete(2L);
        addresses = addressDAO.getAllAddress();
        Assert.assertEquals(1, addresses.size());
    }

    @Test
    @Sql("/clean.sql")
    public void testFindAllAddress() {
        Person person = getPerson("RAM", "RAJ", 1L);
        Address address = getAddress("city", "state", "pincode", "street", 1L, person);
        Address address2 = getAddress("city2", "state2", "pincode2", "street2", 2L, person);
        Address address3 = getAddress("city3", "state3", "pincode3", "street3", 3L, person);
        personDAO.create(person);
        addressDAO.createAddress(address);
        addressDAO.createAddress(address2);
        addressDAO.createAddress(address3);
        List<Address> addresses = addressDAO.getAllAddress();
        Assert.assertEquals(3, addresses.size());
    }
}
