package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.TableDao;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.Table;

import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService{
    @Autowired
    private TableDao tableDao;

    @Value("${meal.time}")
    private int mealTime;

    public void save(Table table){
        tableDao.save(table);
    }

    public List<Table> findByRestaurant(Restaurant restaurant){
        List<Table> listTable = tableDao.findByRestaurant(restaurant);
        return listTable;
    }

    public List<Table> findEmptyTableByRestaurant(Restaurant restaurant, String bookingDatetime, int quantity){
        List<Table> listTable = tableDao.findTableEmptyByRestaurantAndByBookingTime(restaurant.getId(), bookingDatetime, mealTime, quantity);
        return listTable;
    }

    @Override
    public Table findTopByCodeContains(String prefix) {
        Table table = tableDao.findTopByCodeContainsOrderByCodeDesc(prefix);
        return table;
    }

    @Override
    public void deleteById(int id) {
        tableDao.deleteById(id);
    }

    public Table findById(int id){
        return tableDao.findById(id);
    }
}
