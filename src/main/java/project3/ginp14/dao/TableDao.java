package project3.ginp14.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.Table;

import java.util.List;

@Repository
public interface TableDao extends CrudRepository<Table, Integer> {
    List<Table> findByRestaurant(Restaurant restaurant);
    @Query(value = "select * from tables where code like ?1 " +
            "order by code desc " +
            "limit 1", nativeQuery = true)
    Table findTopByCodeContainsOrderByCodeDesc(String prefix);

    @Query(value = "SELECT a.* FROM " +
            "(SELECT * FROM tables WHERE restaurant_id = 1) a " +
            "LEFT JOIN " +
            "(SELECT t.* FROM tables t " +
            " LEFT JOIN bookings b on t.id = b.table_id " +
            " WHERE TIMESTAMPDIFF(MINUTE, CONCAT(b.booking_datetime, ':00'), CONCAT(?2 ,':00')) < ?3 " +
            "AND TIMESTAMPDIFF(MINUTE, CONCAT(b.booking_datetime, ':00'), CONCAT(?2 ,':00')) > -?3) b ON a.id = b.id " +
            "WHERE b.id is null " +
            "AND (a.quantity - ?4 ) >= 0 " +
            "AND (a.quantity - ?4 ) = (SELECT MIN(a.quantity - ?4 ) FROM `tables` where restaurant_id = ?1)", nativeQuery = true)
    List<Table> findTableEmptyByRestaurantAndByBookingTime(int restaurantId, String bookingTime, int mealTime, int quantity);

    Table findById(int id);
}
