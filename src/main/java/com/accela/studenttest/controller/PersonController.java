package com.accela.studenttest.controller;

import com.accela.studenttest.model.Person;
import com.accela.studenttest.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService service;

    @ApiOperation(value = "Add a new Person", response = Iterable.class, tags = "addPerson")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 500, message = "internal Error!!!")})
    @PostMapping("/rest/api/person")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        log.info("add new person invoked {}", person);
        String responseMessage = service.addPerson(person);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Persons", response = Iterable.class, tags = "getPersons")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 500, message = "internal Error!!!")})
    @GetMapping("/rest/api/person")
    public ResponseEntity<List<Person>> getAllPersons() {
        log.info("find all persons invoked");
        List<Person> allPersons = service.findAllPersons();
        return new ResponseEntity<>(allPersons, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Person By ID", response = Iterable.class, tags = "getPersonById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 204, message = "No Data for given ID!!!")})
    @GetMapping("/rest/api/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.info("find person for {} invoked", id);
        Person person = service.findPerson(id);
        return person != null ? new ResponseEntity<>(person, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Delete Person By ID", response = Iterable.class, tags = "deletePersonById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @DeleteMapping("/rest/api/person/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        log.info("delete person for {} invoked", id);
        String result = service.deletePerson(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Person", response = Iterable.class, tags = "updatePerson")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @PutMapping("/rest/api/person/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        log.info("update existing person for {} invoked {}", id, person);
        String updatePerson = service.updatePerson(id, person);
        return new ResponseEntity<>(updatePerson, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Total Persons Count", response = Iterable.class, tags = "getPersonCount")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 500, message = "internal Error!!!")})
    @GetMapping("/rest/api/person/count")
    public ResponseEntity<Integer> getPersonsCount() {
        log.info("get persons count invoked");
        List<Person> allPersons = service.findAllPersons();
        return new ResponseEntity<>(allPersons.size(), HttpStatus.OK);
    }
}
