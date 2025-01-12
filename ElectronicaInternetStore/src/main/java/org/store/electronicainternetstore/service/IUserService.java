package org.store.electronicainternetstore.service;

import org.store.electronicainternetstore.entity.User;

import java.util.List;

public interface IUserService {
    public User saveUser(User user);
    public User saveAdmin(User admin);
    public List<User> findAllAdmins();
    public List<User> findAllUsers();
    public void removeSessionMessage();
}
