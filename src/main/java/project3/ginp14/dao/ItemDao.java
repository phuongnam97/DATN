package project3.ginp14.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Item;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.enumObj.ItemStatus;

import java.util.List;

@Repository
public interface ItemDao extends CrudRepository<Item, Integer> {
    List<Item> findByBooking(Booking booking);
//    Item findByBookingAndDish(Booking booking, Dish dish);
    Item findById(int id);
    List<Item> findByBookingAndDish(Booking booking, Dish dish);
    List<Item> findByBookingAndDishAndItemStatusOrderByIdDesc(Booking booking, Dish dish, ItemStatus itemStatus);
    void deleteById(int id);
    List<Item> findByItemStatusAndBooking_Restaurant(ItemStatus itemStatus, Restaurant restaurant);
    List<Item> findByItemStatusAndBooking_RestaurantOrderByIdAsc(ItemStatus itemStatus, Restaurant restaurant);
    List<Item> findByItemStatusAndBooking_RestaurantAndDishOrderByIdAsc(ItemStatus itemStatus, Restaurant restaurant, Dish dish);
}
