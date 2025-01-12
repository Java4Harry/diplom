package org.store.electronicainternetstore.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.store.electronicainternetstore.entity.User;
import org.store.electronicainternetstore.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        User newUser = userRepo.save(user);
        return newUser;
    }

    @Override
    public User saveAdmin(User admin) {
        String password = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(password);
        admin.setRole("ROLE_ADMIN");
        User newAdmin = userRepo.save(admin);
        return newAdmin;
    }

    @Override
    public List<User> findAllAdmins() {
        List<User> users = userRepo.findAll();
        List<User> admins = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_ADMIN_PAUSE")) {
                admins.add(user);
            }
        }
        return admins;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = userRepo.findAll();
        List<User> users = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getRole().equals("ROLE_USER") || user.getRole().equals("ROLE_USER_PAUSE")) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.removeAttribute("msg");
        session.removeAttribute("successMsg");
        session.removeAttribute("errorMsg");
    }
}
