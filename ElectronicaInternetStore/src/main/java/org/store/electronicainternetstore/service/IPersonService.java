package org.store.electronicainternetstore.service;

import org.store.electronicainternetstore.entity.Person;
import org.store.electronicainternetstore.entity.User;

import java.util.List;

public interface IPersonService {
    public Person createPerson(String email);
    public List<Person> findAllPersonOfAdmins();
    public List<Person> findAllPersonOfUsers();
    public Person savePerson(Person person);
    public Person saveAdmin(Person adminInfo);
}
