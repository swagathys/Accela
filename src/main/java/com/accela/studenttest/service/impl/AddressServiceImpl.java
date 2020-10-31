package com.accela.studenttest.service.impl;

import com.accela.studenttest.dao.AddressDAO;
import com.accela.studenttest.model.Address;
import com.accela.studenttest.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAO addressDAO;

    @Override
    public String addAddress(Address address) {
        boolean result = addressDAO.createAddress(address);
        return result ? "Successfully added new Address" : "Failed to add new Address";
    }

    @Override
    public String updateAddress(Long id, Address address) {
        boolean result = addressDAO.updateAddress(id, address);
        return result ? "Successfully updated Address" : "Failed to update Address";
    }

    @Override
    public String deleteAddress(Long id) {
        boolean result = addressDAO.delete(id);
        return result ? "Successfully deleted" : "Failed to delete {}" + id;
    }

    @Override
    public Address findAddress(Long id) {
        return addressDAO.getAddressById(id);
    }

    @Override
    public List<Address> findAllAddress() {
        return addressDAO.getAllAddress();
    }
}
