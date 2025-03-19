package edu.icet.solorent.service;

import edu.icet.solorent.dto.Vehicle;
import edu.icet.solorent.entity.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    void add(Vehicle vehicle);

    void delete(Long id);

    void update(Vehicle vehicle);

    Optional<VehicleEntity> searchById(Long id);

    List<Vehicle> getAll();
}
