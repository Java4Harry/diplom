package org.store.electronicainternetstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.store.electronicainternetstore.entity.Person;
import org.store.electronicainternetstore.entity.User;
import org.store.electronicainternetstore.repository.PersonRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private UserService userService;

    @Override
    public Person createPerson(String email) {
        Person newPerson = new Person();
        newPerson.setEmail(email);
        personRepo.save(newPerson);
        return newPerson;
    }

    @Override
    public Person savePerson(Person person) {
        if (personRepo.findByEmail(person.getEmail()) != null) {
            Person newPerson = personRepo.findByEmail(person.getEmail());
            newPerson.setSurname(person.getSurname());
            newPerson.setName(person.getName());
            newPerson.setFatherName(person.getFatherName());
            newPerson.setMobile(person.getMobile());
            newPerson.setBirthday(person.getBirthday());
            newPerson.setCity(person.getCity());
            newPerson.setAddress(person.getAddress());
            personRepo.save(newPerson);
            return newPerson;
        }
        return null;
    }

    @Override
    public Person saveAdmin(Person adminInfo) {
        if (personRepo.findByEmail(adminInfo.getEmail()) != null) {
            Person newPerson = personRepo.findByEmail(adminInfo.getEmail());
            newPerson.setSurname(adminInfo.getSurname());
            newPerson.setName(adminInfo.getName());
            personRepo.save(newPerson);
            return newPerson;
        }
        return null;
    }

    @Override
    public List<Person> findAllPersonOfAdmins() {
        List<User> admins = userService.findAllAdmins();
        List<Person> personsAdmins = new ArrayList<>();
        for (User admin : admins) {
                personsAdmins.add(personRepo.findByEmail(admin.getEmail()));
        }
        return personsAdmins;
    }

    @Override
    public List<Person> findAllPersonOfUsers() {
        List<User> users = userService.findAllUsers();
        List<Person> personsUsers = new ArrayList<>();
        for (User user : users) {
            personsUsers.add(personRepo.findByEmail(user.getEmail()));
        }
        return personsUsers;
    }
}
