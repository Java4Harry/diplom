package org.store.electronicainternetstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.electronicainternetstore.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    public boolean existsByName(String name);
}
