package project3.ginp14.service;

import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.Table;

import java.util.List;

public interface TableService {
    void save(Table table);
    public List<Table> findByRestaurant(Restaurant restaurant);
    public Table findTopByCodeContains(String prefix);
    public List<Table> findEmptyTableByRestaurant(Restaurant restaurant, String bookingDatetime, int quantity);
    public void deleteById(int id);
    public Table findById(int id);
}
