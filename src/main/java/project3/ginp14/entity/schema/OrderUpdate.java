package project3.ginp14.entity.schema;

public class OrderUpdate {
    private int orderId;
    private int quantity;
    private int dishId;

    public OrderUpdate() {
    }

    public OrderUpdate(int orderId, int quantity) {
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public OrderUpdate(int orderId, int quantity, int dishId) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.dishId = dishId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
