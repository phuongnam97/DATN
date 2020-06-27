package project3.ginp14.service;

import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Item;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.enumObj.ItemStatus;

import java.util.List;

public interface ItemService {
    List<Item> findByBooking(Booking booking);
    List<Item> findByBookingAndDish(Booking booking, Dish dish);
    void save(Item item);
    List<Item> findByBookingAndDishAndItemStatus(Booking booking, Dish dish, ItemStatus itemStatus);
    void deleteById(int id);
    List<Item> findByItemStatusAndBooking_Restaurant(ItemStatus itemStatus, Restaurant restaurant);
    List<Item> findByItemStatusAndBooking_RestaurantOrderById(ItemStatus itemStatus, Restaurant restaurant);
    List<Item> findByItemStatusAndBooking_RestaurantAndDish(ItemStatus itemStatus, Restaurant restaurant, Dish dish);
}
