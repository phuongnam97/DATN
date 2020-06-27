package project3.ginp14.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project3.ginp14.entity.DishType;

import java.util.List;

public interface DishTypeService {
    List<DishType> findAll();
    Page<DishType> findAll(Pageable pageable);
    void save(DishType dishType);
    DishType findById(int id);
    public void deleteById(int id);
}
