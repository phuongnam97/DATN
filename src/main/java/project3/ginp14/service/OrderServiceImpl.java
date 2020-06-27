package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.OrderDao;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Order;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> findByBooking(Booking booking) {
        return orderDao.findByBooking(booking);
    }

    @Override
    public Order findByBookingAndDish(Booking booking, Dish dish) {
        return orderDao.findByBookingAndDish(booking, dish);
    }

    @Override
    public Order findById(int id) {
        return orderDao.findById(id);
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }
}
