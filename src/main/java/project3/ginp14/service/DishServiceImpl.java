package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.DishDao;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Restaurant;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{
    @Autowired
    private DishDao dishDao;

    @Override
    public List<Dish> findByRestaurant(Restaurant restaurant) {
        List<Dish> listTable = dishDao.findByRestaurant(restaurant);
        return listTable;
    }

    @Override
    public void save(Dish dish) {
        dishDao.save(dish);
    }

    @Override
    public Dish findById(int id) {
        return dishDao.findById(id);
    }

    @Override
    public void deleteById(int id) {
        dishDao.deleteById(id);
    }

    public Page<Dish> findAllbyRestaurant(Pageable pageable, Restaurant restaurant) {
        return dishDao.findByRestaurant(pageable, restaurant);
    }
}
