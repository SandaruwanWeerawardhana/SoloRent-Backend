package edu.icet.solorent.service;

import edu.icet.solorent.dto.Review;
import edu.icet.solorent.dto.Vehicle;

import java.util.List;

public interface VehicleService {
    void add(Vehicle vehicle);

    void delete(Long id);

    void update(Vehicle vehicle);

    Vehicle searchById(Long id);

    List<Vehicle> getAll();
}
