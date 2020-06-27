package project3.ginp14.entity.schema;

import project3.ginp14.entity.Booking;

import java.util.List;

public class TicketDetail {
    private Booking booking;
    private List<DishOrderServed> listDishOrderServed;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public List<DishOrderServed> getListDishOrderServed() {
        return listDishOrderServed;
    }

    public void setListDishOrderServed(List<DishOrderServed> listDishOrderServed) {
        this.listDishOrderServed = listDishOrderServed;
    }

    public TicketDetail(Booking booking, List<DishOrderServed> listDishOrderServed) {
        this.booking = booking;
        this.listDishOrderServed = listDishOrderServed;
    }

    public TicketDetail() {
    }
}
