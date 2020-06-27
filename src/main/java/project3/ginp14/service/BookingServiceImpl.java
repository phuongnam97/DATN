package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.BookingDao;
import project3.ginp14.entity.Booking;
import project3.ginp14.entity.Restaurant;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingDao bookingDao;

    @Override
    public void save(Booking booking) {
        bookingDao.save(booking);
    }

    @Override
    public List<Booking> findAllByRestaurantId(Restaurant restaurant) {
        return bookingDao.findAllByRestaurantOrderByBookingDatetimeDesc(restaurant);
    }

    @Override
    public Booking findById(int id) {
        return bookingDao.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        try{
            bookingDao.deleteById(id);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public int getTotalSeatHold(int restaurantId) {
        return bookingDao.getTotalSeatHold(restaurantId);
    }

    @Override
    public int countAllByGuestTelephone(String telephone) {
        bookingDao.countAllByGuestTelephone(telephone);
        return bookingDao.countAllByGuestTelephone(telephone);
    }

    @Override
    public int countAllByRestaurantAndVerifyStatus(Restaurant restaurant, int verifyStatus) {
        return bookingDao.countAllByRestaurantAndVerifyStatus(restaurant, verifyStatus);
    }

    @Override
    public Page<Booking> findAllByGuestNameAndGuestTelephone(String guestName, String telephone, Pageable pageable) {
        return bookingDao.findAllByGuestNameAndGuestTelephone(guestName, telephone, pageable);
    }
}
