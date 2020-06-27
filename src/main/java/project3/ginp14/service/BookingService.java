package project3.ginp14.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Restaurant;

import java.util.List;

public interface BookingService {
    void save(Booking booking);
    List<Booking> findAllByRestaurantId(Restaurant restaurant);
    Booking findById(int id);
    boolean deleteById(int id);
    int getTotalSeatHold(int restaurantId);
    int countAllByGuestTelephone(String telephone);
    int countAllByRestaurantAndVerifyStatus(Restaurant restaurant, int verifyStatus);
    Page<Booking> findAllByGuestNameAndGuestTelephone(String guestName, String telephone, Pageable pageable);
}
