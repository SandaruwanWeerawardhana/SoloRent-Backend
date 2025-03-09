package edu.icet.solorent.repository;

import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity,Long> {
}
