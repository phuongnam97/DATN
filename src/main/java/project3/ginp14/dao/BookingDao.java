package project3.ginp14.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Restaurant;

import java.util.List;

@Repository
public interface BookingDao extends CrudRepository<Booking, Integer> {
    Booking findByGuestNameOrGuestTelephone(String guestname, String telephone);
    List<Booking> findAllByRestaurantOrderByBookingDatetimeDesc(Restaurant restaurant);
    Booking findById(int id);

    @Query(value = "SELECT CASE WHEN sum(quantity) IS NULL THEN 0 ELSE sum(quantity) END FROM bookings  b " +
            "WHERE TIME_TO_SEC(TIMEDIFF(NOW(), STR_TO_DATE (CONCAT (booking_datetime,':00'), '%Y-%m-%d %T')))/60 < 60 " +
            "AND TIME_TO_SEC(TIMEDIFF(NOW(), STR_TO_DATE (CONCAT (booking_datetime,':00'), '%Y-%m-%d %T')))/60 > 0 " +
            "AND restaurant_id = ?1",nativeQuery = true)
    int getTotalSeatHold(Integer restaurantId);

    int countAllByGuestTelephone(String telephone);
    int countAllByRestaurantAndVerifyStatus(Restaurant restaurant, int verifyStatus);

    Page<Booking> findAllByGuestNameAndGuestTelephoneOrderByBookingDate(String guestName, String telephone, Pageable pageable);

    Page<Booking> findAllByGuestNameAndGuestTelephone(String guestName, String telephone, Pageable pageable);
}
