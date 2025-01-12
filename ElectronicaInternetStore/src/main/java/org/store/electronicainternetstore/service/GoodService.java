package org.store.electronicainternetstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.store.electronicainternetstore.entity.Good;
import org.store.electronicainternetstore.repository.GoodRepo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodService implements IGoodService {

    @Autowired
    private GoodRepo goodRepo;

    @Override
    public Good saveGood(Good good) {
        return goodRepo.save(good);
    }

    @Override
    public List<Good> findAllGoods() {
        return goodRepo.findAll();
    }

    @Override
    public Boolean existGood(int number) {
        return goodRepo.existsByPartNumber(number);
    }

    @Override
    public boolean deleteGood(int id) {
            Good good = goodRepo.findById(id).orElse(null);
            if (good != null) {
                goodRepo.delete(good);
                return true;
            }
            return false;
    }

    @Override
    public Good findGoodById(int id) {
        Good good = goodRepo.findById(id).orElse(null);
        return good;
    }

    @Override
    public Page<Good> findAllGoodsPagination(Integer pageNo, Integer pageSize, String category) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Good> pageGoods = null;
        if (ObjectUtils.isEmpty(category)) {
            pageGoods = goodRepo.findAllBy(pageable);
        } else {
            pageGoods = goodRepo.findByCategory(pageable, category);
        }
        return pageGoods;
    }

    @Override
    public List<Good> findGoodsByCategory(String name) {
        List<Good> goods = goodRepo.findAll();
        List<Good> goodsInCategory = new ArrayList<Good>();
        for (Good good : goods) {
            if (good.getCategory().equals(name)) {
                goodsInCategory.add(good);
            }
        }
        return goodsInCategory;
    }

    @Override
    public List<Good> searchGoods(String ch) {
        return goodRepo.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
    }
}
