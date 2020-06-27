package project3.ginp14.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "guest_telephone")
    private String guestTelephone;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "table_id")
    private project3.ginp14.entity.Table table;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "booking_datetime")
    private String bookingDatetime;

    @Column(name = "booking_detail")
    private String bookingDetail;

    @Column(name = "verify_status")
    private int verifyStatus;

    @Column(name = "check_status")
    private int checkStatus;

    @OneToOne
    @JoinColumn(name="created_by")
    private User createdUser;

    public Booking() {
    }

    public Booking(String guestName, String guestTelephone, int quantity, project3.ginp14.entity.Table table, Restaurant restaurant, String bookingDatetime, String bookingDetail, int verifyStatus, int checkStatus, User createdUser) {
        this.guestName = guestName;
        this.guestTelephone = guestTelephone;
        this.quantity = quantity;
        this.table = table;
        this.restaurant = restaurant;
        this.bookingDatetime = bookingDatetime;
        this.bookingDetail = bookingDetail;
        this.verifyStatus = verifyStatus;
        this.checkStatus = checkStatus;
        this.createdUser = createdUser;
    }

    public Booking(String guestName, String guestTelephone, Restaurant restaurant, String bookingDatetime, String bookingDetail, int verifyStatus, int checkStatus, User createdUser) {
        this.guestName = guestName;
        this.guestTelephone = guestTelephone;
        this.restaurant = restaurant;
        this.bookingDatetime = bookingDatetime;
        this.bookingDetail = bookingDetail;
        this.verifyStatus = verifyStatus;
        this.checkStatus = checkStatus;
        this.createdUser = createdUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestTelephone() {
        return guestTelephone;
    }

    public void setGuestTelephone(String guestTelephone) {
        this.guestTelephone = guestTelephone;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(String bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
    }

    public String getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(String bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public project3.ginp14.entity.Table getTable() {
        return table;
    }

    public void setTable(project3.ginp14.entity.Table table) {
        this.table = table;
    }

    public String getBookingDate(){
        String[] date = this.bookingDatetime.split(" ")[0].split("-");
        return date[2]+"-"+date[1]+"-"+date[0];
    }

    public String getBookingTime(){
        return this.bookingDatetime.split(" ")[1];
    }

    public boolean isOutOfDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dateFormat.format(date);
        Date datetimeBooking;
        try {
            datetimeBooking = dateFormat.parse(this.bookingDatetime + ":00");
        } catch (Exception e){
            return false;
        }
        if (date.after(datetimeBooking)){
            return true;
        } else return false;
    }
}
