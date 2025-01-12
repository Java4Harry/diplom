package org.store.electronicainternetstore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.store.electronicainternetstore.entity.*;
import org.store.electronicainternetstore.repository.CategoryRepo;
import org.store.electronicainternetstore.repository.GoodRepo;
import org.store.electronicainternetstore.repository.PersonRepo;
import org.store.electronicainternetstore.repository.UserRepo;
import org.store.electronicainternetstore.service.CategoryService;
import org.store.electronicainternetstore.service.GoodService;
import org.store.electronicainternetstore.service.PersonService;
import org.store.electronicainternetstore.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private GoodRepo goodRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodService goodService;
    @Autowired
    private CategoryRepo categoryRepo;

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
        return "admin/index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "admin/profile";
    }

    @GetMapping("/editProfileAdmin")
    public String editProfileAdmin() {
        return "admin/profile";
    }

    @GetMapping("/editAdmins")
    public String editAdmins(Model m) {
        m.addAttribute("admins", userService.findAllAdmins());
        m.addAttribute("persons", personService.findAllPersonOfAdmins());
        return "admin/edit_admins";
    }

    @GetMapping("/changeRole/{id}")
    public String changeRole(@PathVariable int id) {
        User user = userRepo.findById(id);
        if(user.getRole().equals("ROLE_ADMIN")) {
            user.setRole("ROLE_ADMIN_PAUSE");
            userRepo.save(user);
        } else {
            user.setRole("ROLE_ADMIN");
            userRepo.save(user);
        }
        return "redirect:/admin/editAdmins";
    }

    @GetMapping("/deleteAdmin/{id}")
    public String deleteAdmin(@PathVariable int id) {
        User user = userRepo.findById(id);
        Person person = personRepo.findByEmail(user.getEmail());
            personRepo.delete(person);
            userRepo.delete(user);
        return "redirect:/admin/editAdmins";
    }

    @PostMapping("/addNewAdmin")
    public String addNewAdmin(@ModelAttribute Admin admin, HttpSession session) {
        if (userRepo.findByEmail(admin.getEmail()) == null && personRepo.findByEmail(admin.getEmail()) == null) {
            User newAdmin = new User();
            newAdmin.setEmail(admin.getEmail());
            newAdmin.setPassword(admin.getPassword());
            User adminCheck = userService.saveAdmin(newAdmin);
            Person newPerson = personService.createPerson(newAdmin.getEmail());
            newPerson.setName(admin.getName());
            newPerson.setSurname(admin.getSurname());
            personService.saveAdmin(newPerson);
            if (adminCheck != null && newPerson != null) {
                session.setAttribute("successMsg", "Администратор добавлен!");
                return "redirect:/admin/editAdmins";
            } else {
                session.setAttribute("errorMsg", "Ошибка добавления данных!");
                return "redirect:/admin/editAdmins";
            }
        } else {
            session.setAttribute("msg", "Администратор с таким email уже существует!");
            return "redirect:/admin/editAdmins";
        }
    }

    @PostMapping("/editAdminProfile")
    public String editAdminProfile(@ModelAttribute Person person, HttpSession session) {
            Person newPerson = personRepo.findById(person.getId());
            newPerson.setName(person.getName());
            newPerson.setSurname(person.getSurname());
            personRepo.save(newPerson);
            if (newPerson != null) {
                session.setAttribute("successMsg", "Данные обновлены!");
                return "admin/profile";
            } else {
                session.setAttribute("errorMsg", "Ошибка обновления данных!");
                return "admin/profile";
        }
    }

    @GetMapping("/editUsers")
    public String editUsers(Model m) {
        m.addAttribute("users", userService.findAllUsers());
        m.addAttribute("persons", personService.findAllPersonOfUsers());
        return "admin/edit_users";
    }

    @GetMapping("/changeRoleUser/{id}")
    public String changeRoleUser(@PathVariable int id) {
        User user = userRepo.findById(id);
        if(user.getRole().equals("ROLE_USER")) {
            user.setRole("ROLE_USER_PAUSE");
            userRepo.save(user);
        } else {
            user.setRole("ROLE_USER");
            userRepo.save(user);
        }
        return "redirect:/admin/editUsers";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        User user = userRepo.findById(id);
        Person person = personRepo.findByEmail(user.getEmail());
        personRepo.delete(person);
        userRepo.delete(user);
        return "redirect:/admin/editUsers";
    }

    @GetMapping("/editCategories")
    public String editCategories(Model m) {
        m.addAttribute("categories", categoryService.findAllCategory());
        return "admin/categories";
    }

    @PostMapping("/addNewCategory")
    public String addNewCategory(@ModelAttribute Category category, HttpSession session, @RequestParam("file") MultipartFile image) throws IOException {
        Boolean existCategory = categoryService.existCategory(category.getName());
        if (existCategory) {
            session.setAttribute("errorMsg", "Категория уже существует");
        } else {
            String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
            category.setImage(imageName);
            Category saveCategory = categoryService.saveCategory(category);
            if (saveCategory != null) {
                String saveFile = new File("src/main/resources/static/images").getAbsolutePath();
                if (!image.isEmpty()) {
                    Path path = Paths.get(saveFile + File.separator + "categories" + File.separator + imageName);
                    if (!Files.exists(path)) {
                        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                session.setAttribute("successMsg", "Категория добавлена!");
            } else {
                session.setAttribute("errorMsg", "Ошибка добаления категории!");
            }
        }
        return "redirect:/admin/editCategories";
    }

    @GetMapping("/editCategoryItem/{id}")
    public String editCategoryItem(@PathVariable int id, Model m){
        m.addAttribute("category", categoryService.findCategoryById(id));
        m.addAttribute("categories", categoryService.findAllCategory());
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, HttpSession session,@RequestParam("file") MultipartFile image) throws IOException{
        Category oldCategory = categoryService.findCategoryById(category.getId());
        if(oldCategory != null){
            oldCategory.setNumber(category.getNumber());
            oldCategory.setName(category.getName());
            String imageName = image.isEmpty() ? oldCategory.getImage() : image.getOriginalFilename();
            oldCategory.setImage(imageName);
            Category updateCategory = categoryService.saveCategory(oldCategory);
            if (updateCategory != null) {
                String saveFile = new File("src/main/resources/static/images").getAbsolutePath();
                if (!image.isEmpty()) {
                    Path path = Paths.get(saveFile + File.separator + "categories" + File.separator + imageName);
                    if (!Files.exists(path)) {
                        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                session.setAttribute("successMsg", "Категория обновлена!");
            } else {
                session.setAttribute("errorMsg", "Ошибка обновления категории!");
            }
        }
        return "redirect:/admin/editCategoryItem/" + oldCategory.getId();
    }


    @GetMapping("/deleteCategoryItem/{id}")
    public String deleteCategoryItem(@PathVariable int id, HttpSession session){
        categoryService.deleteCategory(id);
        return "redirect:/admin/editCategories";
    }

    @GetMapping("/editGoods")
    public String editGoods(Model m) {
        m.addAttribute("goods", goodService.findAllGoods());
        return "admin/goods";
    }

    @PostMapping("/addNewGood")
    public String addNewGood(@ModelAttribute Good good, HttpSession session, @RequestParam("file") MultipartFile image) throws IOException {
        Boolean existGood = goodService.existGood(good.getPartNumber());
        if (existGood) {
            session.setAttribute("errorMsg", "Товар уже существует");
        } else {
            String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
            good.setImage(imageName);
            good.setStatus("show");
            good.setPrice((double) good.getPrice());
            Good saveGood = goodService.saveGood(good);
            if (saveGood != null) {
                String saveFile = new File("src/main/resources/static/images").getAbsolutePath();
                if (!image.isEmpty()) {
                    Path path = Paths.get(saveFile + File.separator + "goods" + File.separator + imageName);
                    if (!Files.exists(path)) {
                        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                session.setAttribute("successMsg", "Товар добавлен!");
            } else {
                session.setAttribute("errorMsg", "Ошибка добаления товара!");
            }
        }
        return "redirect:/admin/editGoods";
    }

    @GetMapping("/editOrders")
    public String editOrders() {
        return "admin/orders";
    }

    @GetMapping("/deleteGoodItem/{id}")
    public String deleteGoodItem(@PathVariable int id, HttpSession session){
        Boolean deleteGoodItem = goodService.deleteGood(id);
        if (deleteGoodItem) {
            session.setAttribute("successMsg", "Товар удален!");
        } else{
            session.setAttribute("errorMsg", "Ошибка удаления товара!");
        }
        return "redirect:/admin/editGoods";
    }

    @GetMapping("/changeGoodStatus/{id}")
    public String changeGoodStatus(@PathVariable int id){
        Good good = goodService.findGoodById(id);
        if(good.getStatus().equals("show")) {
            good.setStatus("disabled");
            goodRepo.save(good);
        } else {
            good.setStatus("show");
            goodRepo.save(good);
        }
        return "redirect:/admin/editGoods";
    }

    @GetMapping("/editGoodItem/{id}")
    public String editGoodItem(@PathVariable int id, Model m){
        m.addAttribute("good", goodService.findGoodById(id));
        return "admin/edit_item";
    }

    @PostMapping("/updateGoodItem")
    public String updateGoodItem(@ModelAttribute Good good, HttpSession session, @RequestParam("file") MultipartFile image) throws IOException {
        Good oldGood = goodService.findGoodById(good.getId());
        String imageName = image.isEmpty() ? oldGood.getImage() : image.getOriginalFilename();
        oldGood.setImage(imageName);
        oldGood.setPartNumber(good.getPartNumber());
        oldGood.setCategory(good.getCategory());
        oldGood.setName(good.getName());
        oldGood.setBrand(good.getBrand());
        oldGood.setDescription(good.getDescription());
        oldGood.setWeight(good.getWeight());
        oldGood.setWidth(good.getWidth());
        oldGood.setLength(good.getLength());
        oldGood.setHeight(good.getHeight());
        oldGood.setPrice(good.getPrice());
        oldGood.setCount(good.getCount());
        Good saveGood = goodService.saveGood(oldGood);
            if (saveGood != null) {
                String saveFile = new File("src/main/resources/static/images").getAbsolutePath();
                if (!image.isEmpty()) {
                    Path path = Paths.get(saveFile + File.separator + "goods" + File.separator + imageName);
                    if (!Files.exists(path)) {
                        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                session.setAttribute("successMsg", "Товар добавлен!");
            } else {
                session.setAttribute("errorMsg", "Ошибка добаления товара!");
            }
        return "redirect:/admin/editGoodItem/" + good.getId();
    }

    @PostMapping("/sortedGoodsByCategory")
    public String sortedGoodsByCategory(@RequestParam("category") String category, Model m) {
        List<Good> sortedGoods = goodService.findGoodsByCategory(category);
        if(category.equals("all")) {
            sortedGoods = goodService.findAllGoods();
        }
        m.addAttribute("goods", sortedGoods);
        return "admin/goods";
    }
}