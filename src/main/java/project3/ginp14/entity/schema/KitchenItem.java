package project3.ginp14.entity.schema;

import project3.ginp14.entity.Dish;

public class KitchenItem {
    private Dish dish;
    private int quantity;

    public KitchenItem() {
    }

    public KitchenItem(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
