package com.accela.studenttest.controller;

import com.accela.studenttest.model.Address;
import com.accela.studenttest.service.AddressService;
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
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "Add a new Address", response = Iterable.class, tags = "addAddress")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 500, message = "internal Error!!!")})
    @PostMapping("/rest/api/address")
    public ResponseEntity<String> addAddress(@RequestBody Address address) {
        log.info("add new address invoked {}", address);
        String addAddress = addressService.addAddress(address);
        return new ResponseEntity<>(addAddress, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Address", response = Iterable.class, tags = "updateAddress")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @PutMapping("/rest/api/address/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        log.info("update existing address for {} invoked {}", id, address);
        String updateAddress = addressService.updateAddress(id, address);
        return new ResponseEntity<>(updateAddress, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Address By ID", response = Iterable.class, tags = "deleteAddressById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @DeleteMapping("/rest/api/address/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        log.info("delete address for {} invoked", id);
        String result = addressService.deleteAddress(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Address By ID", response = Iterable.class, tags = "getAddressById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 204, message = "No Data for given ID!!!")})
    @GetMapping("/rest/api/address/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        log.info("find address for {} invoked", id);
        Address address = addressService.findAddress(id);
        return address != null ? new ResponseEntity<>(address, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get All Address", response = Iterable.class, tags = "getAddresses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!"),
            @ApiResponse(code = 500, message = "internal Error!!!")})
    @GetMapping("/rest/api/address")
    public ResponseEntity<List<Address>> getAllAddresses() {
        log.info("find all addresses invoked");
        List<Address> allAddress = addressService.findAllAddress();
        return new ResponseEntity<>(allAddress, HttpStatus.OK);
    }
}
