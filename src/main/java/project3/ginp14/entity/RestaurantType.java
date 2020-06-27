package project3.ginp14.entity;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="restaurant_type")
public class RestaurantType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "This field cannot be blank")
    private String name;

    @Column(name="description")
    @NotBlank(message = "This field cannot be blank")
    private String description;

    @Column(name = "image")
    @NotBlank(message = "This field cannot be blank")
    private String image;

    public RestaurantType() {
    }

    public RestaurantType(@NotBlank(message = "This field cannot be blank") String name, @NotBlank(message = "This field cannot be blank") String description, @NotBlank(message = "This field cannot be blank") String image) {
        this.name = name;
        this.description = description;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
