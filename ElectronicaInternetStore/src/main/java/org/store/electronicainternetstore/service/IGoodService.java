package org.store.electronicainternetstore.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.store.electronicainternetstore.entity.Good;

import java.util.List;

public interface IGoodService {
    public Good saveGood(Good good);
    public List<Good> findAllGoods();
    public Boolean existGood(int number);
    public boolean deleteGood(int id);
    public Good findGoodById(int id);
    public Page<Good> findAllGoodsPagination(Integer pageNo, Integer pageSize, String category);
    public List<Good> findGoodsByCategory(String name);
    public List<Good> searchGoods(String ch);

}
