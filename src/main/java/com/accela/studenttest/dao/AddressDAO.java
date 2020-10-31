package com.accela.studenttest.dao;

import com.accela.studenttest.model.Address;

import java.util.List;

public interface AddressDAO {
    boolean createAddress(Address address);

    boolean updateAddress(Long id, Address address);

    Address getAddressById(long id);

    List<Address> getAllAddress();

    boolean delete(long id);
}
