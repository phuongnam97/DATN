package project3.ginp14.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.RestaurantType;

import java.util.List;

@Repository
public interface RestaurantTypeDao extends CrudRepository<RestaurantType, Integer> {
    List<RestaurantType> findAll();
    RestaurantType findById(int id);
    Page<RestaurantType> findAll(Pageable pageable);
}
