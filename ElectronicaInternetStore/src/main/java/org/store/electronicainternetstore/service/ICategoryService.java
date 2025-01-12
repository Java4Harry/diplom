package org.store.electronicainternetstore.service;

import org.store.electronicainternetstore.entity.Category;

import java.util.List;

public interface ICategoryService {
    public Category saveCategory(Category category);
    public List<Category> findAllCategory();
    public List<Category> findAllSortedCategory();
    public Boolean existCategory(String name);
    public Boolean deleteCategory(int id);
    public Category findCategoryById(int id);
}
