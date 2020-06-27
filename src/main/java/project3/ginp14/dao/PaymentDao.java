package project3.ginp14.dao;

import org.springframework.data.repository.CrudRepository;
import project3.ginp14.entity.Payment;

import java.util.List;

public interface PaymentDao extends CrudRepository<Payment, Integer> {
    List<Payment> findAll();
}
