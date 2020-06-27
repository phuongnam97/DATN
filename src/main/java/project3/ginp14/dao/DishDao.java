package project3.ginp14.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Restaurant;

import java.util.List;

@Repository
public interface DishDao extends CrudRepository<Dish, Integer> {
    List<Dish> findByRestaurant(Restaurant restaurant);
    Dish findById(int id);
    Page<Dish> findByRestaurant(Pageable pageable, Restaurant restaurant);
}
