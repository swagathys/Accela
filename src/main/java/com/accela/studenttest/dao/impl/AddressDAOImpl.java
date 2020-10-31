package com.accela.studenttest.dao.impl;

import com.accela.studenttest.dao.AddressDAO;
import com.accela.studenttest.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
@Transactional
public class AddressDAOImpl implements AddressDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean createAddress(Address address) {
        try {
            entityManager.persist(address);
        } catch (HibernateException ex) {
            log.error("Exception during persisting entity {}", ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateAddress(Long id, Address address) {
        try {
            Address addressById = entityManager.find(Address.class, id);
            if (addressById != null) {
                addressById.setStreet(address.getStreet());
                addressById.setPostal_code(address.getPostal_code());
                addressById.setState(address.getState());
                addressById.setCity(address.getCity());
            }
        } catch (HibernateException ex) {
            log.error("Exception during persisting entity {}", ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public Address getAddressById(long id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public List<Address> getAllAddress() {
        return entityManager.createQuery("FROM Address").getResultList();
    }

    @Override
    public boolean delete(long id) {
        Address address = getAddressById(id);
        if (address != null) {
            try {
                entityManager.remove(address);
            } catch (HibernateException ex) {
                log.error("Exception during DELETING entity {}", ex.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }
}
