package edu.icet.solorent.repository;

import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
}
