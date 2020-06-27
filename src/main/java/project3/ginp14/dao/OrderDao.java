package project3.ginp14.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Order;

import java.util.List;

@Repository
public interface OrderDao extends CrudRepository<Order, Integer> {
    List<Order> findByBooking(Booking booking);
    Order findByBookingAndDish(Booking booking, Dish dish);
    Order findById(int id);
}
