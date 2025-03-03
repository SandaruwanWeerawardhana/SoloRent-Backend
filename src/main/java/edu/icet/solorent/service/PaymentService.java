package edu.icet.solorent.service;

import edu.icet.solorent.dto.Payment;

import java.util.List;

public interface PaymentService {
    void add(Payment payment);

    void delete(Long id);

    void update(Payment payment);

    Payment searchById(Long id);

    List<Payment> getAll();
}
