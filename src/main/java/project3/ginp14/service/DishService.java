package project3.ginp14.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Restaurant;

import java.util.List;

public interface DishService {
    public List<Dish> findByRestaurant(Restaurant restaurant);
    public void save(Dish dish);
    public Dish findById(int id);
    public void deleteById(int id);
    public Page<Dish> findAllbyRestaurant(Pageable pageable, Restaurant restaurant);
}
