package edu.icet.solorent.service;

import edu.icet.solorent.dto.Payment;
import edu.icet.solorent.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    void add(Payment payment);

    void delete(Long id);

    void update(Payment payment);

    Optional<PaymentEntity> searchById(Long id);

    List<Payment> getAll();
}
