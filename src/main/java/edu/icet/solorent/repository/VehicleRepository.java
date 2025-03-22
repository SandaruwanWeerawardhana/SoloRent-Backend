package edu.icet.solorent.repository;

import edu.icet.solorent.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    VehicleEntity findByBrand(String brand);
}
