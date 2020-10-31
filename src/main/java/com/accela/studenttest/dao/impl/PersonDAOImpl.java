package com.accela.studenttest.dao.impl;

import com.accela.studenttest.dao.PersonDAO;
import com.accela.studenttest.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
@Transactional
public class PersonDAOImpl implements PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Person person) {
        try {
            entityManager.persist(person);
        } catch (HibernateException ex) {
            log.error("Exception during persisting entity {}", ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Long id, Person person) {
        try {
            Person personById = getPersonById(id);
            if (personById != null) {
                personById.setFirstName(person.getFirstName());
                personById.setLastName(person.getLastName());
            }
        } catch (HibernateException ex) {
            log.error("Exception during persisting entity {}", ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public Person getPersonById(long id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> getAllPersons() {
        return entityManager.createQuery("FROM Person").getResultList();
    }

    @Override
    public boolean delete(long id) {
        Person person = getPersonById(id);
        if (person != null) {
            try {
                entityManager.remove(person);
            } catch (HibernateException ex) {
                log.error("Exception during DELETING entity {}", ex.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }
}
