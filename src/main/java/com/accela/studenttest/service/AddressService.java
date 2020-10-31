package com.accela.studenttest.service;

import com.accela.studenttest.model.Address;
import com.accela.studenttest.model.Person;

import java.util.List;

public interface AddressService {

    String addAddress(Address address);

    String updateAddress(Long id, Address address);

    String deleteAddress(Long id);

    Address findAddress(Long id);

    List<Address> findAllAddress();

}
