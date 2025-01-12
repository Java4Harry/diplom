package org.store.electronicainternetstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.electronicainternetstore.entity.Person;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    public Person findByEmail(String email);
    public Person findById(int id);
}
