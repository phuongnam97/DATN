package project3.ginp14.entity;

import project3.ginp14.entity.enumObj.ItemStatus;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item implements Comparable<Item>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "status")
    private ItemStatus itemStatus;

    public Item() {
    }

    public Item(Booking booking, Dish dish) {
        this.booking = booking;
        this.dish = dish;
    }

    public Item(Booking booking, Dish dish, ItemStatus itemStatus) {
        this.booking = booking;
        this.dish = dish;
        this.itemStatus = itemStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Override
    public int compareTo(Item o) {
        if (this.dish.getId() > o.getDish().getId()){
            return 1;
        } else if (this.dish.getId() < o.getDish().getId())return -1;
        else return 0;
    }
}
