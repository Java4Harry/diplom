package org.store.electronicainternetstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.store.electronicainternetstore.entity.Good;

import java.util.List;

public interface GoodRepo extends JpaRepository<Good, Integer> {
    boolean existsByPartNumber(int partNumber);
    List<Good> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2);
    Page<Good> findByCategory (Pageable pageable, String category);
    Page<Good> findAllBy (Pageable pageable);
}
