package edu.icet.solorent.repository;

import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintetanceRepositry extends JpaRepository<MaintenanceEntity,Long> {
}
