package org.store.electronicainternetstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.store.electronicainternetstore.entity.Category;
import org.store.electronicainternetstore.repository.CategoryRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public List<Category> findAllSortedCategory() {
        List<Category> categories = categoryRepo.findAll();
        List<Category> sortedCategories = new ArrayList<>();
        int min;
        int index = 0;
        while (!categories.isEmpty()) {
            min = Integer.MAX_VALUE;
            for(int i = 0; i < categories.size(); i++) {
                if(categories.get(i).getNumber() < min) {
                    min = categories.get(i).getNumber();
                    index = i;
                }
            }
            sortedCategories.add(categories.get(index));
            categories.remove(categories.get(index));
        }
        return sortedCategories;
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepo.existsByName(name);
    }

    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category != null) {
            categoryRepo.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public Category findCategoryById(int id) {
        Category category = categoryRepo.findById(id).orElse(null);
        return category;
    }
}
