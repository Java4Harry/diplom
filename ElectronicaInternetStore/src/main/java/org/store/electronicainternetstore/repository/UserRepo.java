package org.store.electronicainternetstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.electronicainternetstore.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
    public User findById(int id);
}
