package org.store.electronicainternetstore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.store.electronicainternetstore.entity.Category;
import org.store.electronicainternetstore.entity.Person;
import org.store.electronicainternetstore.entity.User;
import org.store.electronicainternetstore.repository.PersonRepo;
import org.store.electronicainternetstore.repository.UserRepo;
import org.store.electronicainternetstore.service.CategoryService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PersonRepo personRepo;

    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepo.findByEmail(email);
            Person person = personRepo.findByEmail(email);
            model.addAttribute("user", user);
            model.addAttribute("person", person);
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @PostMapping("/editUserProfile")
    public String editUserProfile(@ModelAttribute Person person, HttpSession session) {
        Person newPerson = personRepo.findById(person.getId());
        newPerson.setName(person.getName());
        newPerson.setSurname(person.getSurname());
        newPerson.setFatherName(person.getFatherName());
        newPerson.setMobile(person.getMobile());
        newPerson.setBirthday(person.getBirthday());
        newPerson.setCity(person.getCity());
        newPerson.setAddress(person.getAddress());
        personRepo.save(newPerson);
        if (newPerson != null) {
            session.setAttribute("successMsg", "Данные обновлены!");
            return "redirect:/user/profile";
        } else {
            session.setAttribute("errorMsg", "Ошибка обновления данных!");
            return "redirect:/user/profile";
        }
    }
}
