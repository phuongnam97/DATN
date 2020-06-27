package project3.ginp14.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.RestaurantType;
import project3.ginp14.entity.User;

import java.util.List;

public interface RestaurantService {
    Restaurant findById(int id);
    Restaurant findByUserId(int id);
    void save(Restaurant restaurant);
    Page<Restaurant> findAllByRestaurantType(RestaurantType restaurantType, Pageable pageable);
    Page<Restaurant> findAll(Pageable pageable);
}
