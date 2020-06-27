package project3.ginp14.service;

import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByBooking(Booking booking);
    Order findByBookingAndDish(Booking booking, Dish dish);
    Order findById(int id);
    void save(Order order);
}
