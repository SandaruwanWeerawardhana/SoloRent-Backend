package edu.icet.solorent.repository;

import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {
}
