package project3.ginp14.entity;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;

@Entity
@Table(name="restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "This field cannot be blank")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "This field cannot be blank")
    private String description;

    @Column(name = "average_price_min")
    @NotNull(message = "This field cannot be blank")
    private int averagePriceMin;

    @Column(name = "average_price_max")
    @NotNull(message = "This field cannot be blank")
    private int averagePriceMax;

    @Column(name = "images")
    private String images;

    @Column(name="telephone")
    private String telephone;

    @Column(name="address")
    private String address;

    @Column(name="number_of_seat")
    private int numberOfSeat;

    @Column(name="no_of_seat_left")
    private int numberOfSeatLeft;

    @OneToOne
    @JoinColumn(name="restaurant_type_id")
    private RestaurantType restaurantType;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public Restaurant() {
    }

    public Restaurant(@NotBlank(message = "This field cannot be blank") String name, @NotBlank(message = "This field cannot be blank") String description, @NotBlank(message = "This field cannot be blank") int averagePriceMin, @NotBlank(message = "This field cannot be blank") int averagePriceMax, String images, String telephone, String address, int numberOfSeat, int numberOfSeatLeft, RestaurantType restaurantType) {
        this.name = name;
        this.description = description;
        this.averagePriceMin = averagePriceMin;
        this.averagePriceMax = averagePriceMax;
        this.images = images;
        this.telephone = telephone;
        this.address = address;
        this.numberOfSeat = numberOfSeat;
        this.numberOfSeatLeft = numberOfSeatLeft;
        this.restaurantType = restaurantType;
    }

    public Restaurant(@NotBlank(message = "This field cannot be blank") String name, @NotBlank(message = "This field cannot be blank") String description, @NotBlank(message = "This field cannot be blank") int averagePriceMin, @NotBlank(message = "This field cannot be blank") int averagePriceMax, String images, String telephone, String address, int numberOfSeat, int numberOfSeatLeft, RestaurantType restaurantType, User user) {
        this.name = name;
        this.description = description;
        this.averagePriceMin = averagePriceMin;
        this.averagePriceMax = averagePriceMax;
        this.images = images;
        this.telephone = telephone;
        this.address = address;
        this.numberOfSeat = numberOfSeat;
        this.numberOfSeatLeft = numberOfSeatLeft;
        this.restaurantType = restaurantType;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAveragePriceMin() {
        return averagePriceMin;
    }

    public void setAveragePriceMin(int averagePriceMin) {
        this.averagePriceMin = averagePriceMin;
    }

    public int getAveragePriceMax() {
        return averagePriceMax;
    }

    public void setAveragePriceMax(int averagePriceMax) {
        this.averagePriceMax = averagePriceMax;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public int getNumberOfSeatLeft() {
        return numberOfSeatLeft;
    }

    public void setNumberOfSeatLeft(int numberOfSeatLeft) {
        this.numberOfSeatLeft = numberOfSeatLeft;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMaxPriceOnView(){
        String newPriceMax = String.valueOf(this.averagePriceMax);
        Double amount = Double.parseDouble(newPriceMax);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount);
    }

    public String getMinPriceOnView(){
        String newPriceMin = String.valueOf(this.averagePriceMin);
        Double amount = Double.parseDouble(newPriceMin);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount);
    }
}
