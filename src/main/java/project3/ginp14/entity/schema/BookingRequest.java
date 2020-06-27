package project3.ginp14.entity.schema;

public class BookingRequest {
    private int quantity;
    private String bookingDateTime;

    public BookingRequest() {
    }

    public BookingRequest(int quantity, String bookingDateTime) {
        this.quantity = quantity;
        this.bookingDateTime = bookingDateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }
}
