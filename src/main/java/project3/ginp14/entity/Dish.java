package project3.ginp14.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "dishtype_id")
    private DishType dishType;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Dish(String name, int price, String image, String description, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.restaurant = restaurant;
    }

    public Dish(String name, int price, String image, String description, DishType dishType, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.dishType = dishType;
        this.restaurant = restaurant;
    }

    public Dish() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public String getPriceString(){
        String priceString = String.valueOf(this.price);
        Double amount = Double.parseDouble(priceString);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " VNĐ";
    }
}
