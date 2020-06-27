package project3.ginp14.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "list_order")
    private String listOrderId;

    @Column(name = "tax")
    private int tax;

    @Column(name = "sum")
    private int sum;

    @Column(name = "principal_sum")
    private int principalSum;

    @Column(name = "create_at")
    private Date createAt;

    public Payment(String listOrderId, int tax, int sum, int principalSum, Date createAt) {
        this.listOrderId = listOrderId;
        this.tax = tax;
        this.sum = sum;
        this.principalSum = principalSum;
        this.createAt = createAt;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListOrderId() {
        return listOrderId;
    }

    public void setListOrderId(String listOrderId) {
        this.listOrderId = listOrderId;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPrincipalSum() {
        return principalSum;
    }

    public void setPrincipalSum(int principalSum) {
        this.principalSum = principalSum;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
