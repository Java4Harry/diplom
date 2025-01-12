package org.store.electronicainternetstore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.store.electronicainternetstore.entity.Category;
import org.store.electronicainternetstore.entity.Good;
import org.store.electronicainternetstore.entity.Person;
import org.store.electronicainternetstore.entity.User;
import org.store.electronicainternetstore.repository.CategoryRepo;
import org.store.electronicainternetstore.repository.PersonRepo;
import org.store.electronicainternetstore.repository.UserRepo;
import org.store.electronicainternetstore.service.CategoryService;
import org.store.electronicainternetstore.service.GoodService;
import org.store.electronicainternetstore.service.PersonService;
import org.store.electronicainternetstore.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PersonService personService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodService goodService;

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

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/payDelivery")
    public String payDelivery() {
        return "pay_delivery";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "register";
    }

    @PostMapping("/editFirstProfile")
    public String editFirstProfile(@ModelAttribute Person person, HttpSession session) {
        Person newPerson = personService.savePerson(person);
        if (newPerson != null) {
            session.setAttribute("successMsg", "Вы зарегистрированы!");
            return "index";
        } else {
            session.setAttribute("errorMsg", "Ошибка добавления данных!");
        }
        return "profile";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session) {
        if (userRepo.findByEmail(user.getEmail()) == null && personRepo.findByEmail(user.getEmail()) == null) {
            User newUser = userService.saveUser(user);
            Person newPerson = personService.createPerson(user.getEmail());
            if (newUser != null && newPerson != null) {
                session.setAttribute("successMsg", "Добавьте Ваши персональные данные");
                return "profile";
            } else {
                session.setAttribute("errorMsg", "Ошибка регистрации!");
                return "redirect:/registration";
            }
        } else {
            session.setAttribute("msg", "Пользователь с таким email уже существует!");
            return "redirect:/registration";
        }
    }

    @GetMapping("/goToCategoryPage/{id}")
    public String goToCategoryPage(@PathVariable int id, Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize) {
        Category category = categoryService.findCategoryById(id);
        m.addAttribute("category", category);
        Page<Good> page = goodService.findAllGoodsPagination(pageNo, pageSize, category.getName());
        List<Good> goods = page.getContent();
        m.addAttribute("goodsInCategory", goods);
        m.addAttribute("goodsSize", goodService.findGoodsByCategory(category.getName()).size());
        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalGoods", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isLast", page.isLast());
        m.addAttribute("isFirst", page.isFirst());
        return "category_page";
    }

    @GetMapping("/searchItem")
    public String searchItem(@RequestParam String search, Model m){
        List<Good> searchGoods = goodService.searchGoods(search);
        m.addAttribute("search", searchGoods);
        return "search_page";
    }

    @GetMapping("/goToItemPage/{id}")
    public String goToItemPage(@PathVariable int id, Model m) {
        Good good = goodService.findGoodById(id);
        m.addAttribute("good", good);
        return "good_page";
    }
}

