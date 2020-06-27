package project3.ginp14.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.DishType;

import java.util.List;

@Repository
public interface DishTypeDao extends CrudRepository<DishType ,Integer> {
    List<DishType> findAll();
    DishType findById(int id);
    Page<DishType> findAll(Pageable pageable);
}
