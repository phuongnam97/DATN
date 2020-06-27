package project3.ginp14.entity.schema;

public class ProcessItemRequest {
    private int dishId;
    private int quantity;
    private String type;

    public ProcessItemRequest() {
    }

    public ProcessItemRequest(int dishId, int quantity, String type) {
        this.dishId = dishId;
        this.quantity = quantity;
        this.type = type;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
