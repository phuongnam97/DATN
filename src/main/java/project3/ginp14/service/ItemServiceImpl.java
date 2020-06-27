package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.ItemDao;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Dish;
import project3.ginp14.entity.Item;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.enumObj.ItemStatus;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> findByBooking(Booking booking) {
        List<Item> listItem = itemDao.findByBooking(booking);
        return listItem;
    }

    @Override
    public List<Item> findByBookingAndDish(Booking booking, Dish dish) {
        List<Item> listItem = itemDao.findByBookingAndDish(booking, dish);
        return listItem;
    }

    @Override
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    public List<Item> findByBookingAndDishAndItemStatus(Booking booking, Dish dish, ItemStatus itemStatus) {
        List<Item> listItem = itemDao.findByBookingAndDishAndItemStatusOrderByIdDesc(booking, dish, itemStatus);
        return listItem;
    }

    @Override
    public void deleteById(int id) {
        itemDao.deleteById(id);
    }

    @Override
    public List<Item> findByItemStatusAndBooking_Restaurant(ItemStatus itemStatus, Restaurant restaurant) {
        return itemDao.findByItemStatusAndBooking_Restaurant(itemStatus, restaurant);
    }

    @Override
    public List<Item> findByItemStatusAndBooking_RestaurantOrderById(ItemStatus itemStatus, Restaurant restaurant) {
        return itemDao.findByItemStatusAndBooking_RestaurantOrderByIdAsc(itemStatus, restaurant);
    }

    @Override
    public List<Item> findByItemStatusAndBooking_RestaurantAndDish(ItemStatus itemStatus, Restaurant restaurant, Dish dish) {
        return itemDao.findByItemStatusAndBooking_RestaurantAndDishOrderByIdAsc(itemStatus, restaurant, dish);
    }
}
