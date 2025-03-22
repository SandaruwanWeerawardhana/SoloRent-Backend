package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Vehicle;
import edu.icet.solorent.entity.VehicleEntity;
import edu.icet.solorent.repository.VehicleRepository;
import edu.icet.solorent.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository repository;
    private final ModelMapper mapper;


    @Override
    public void add(Vehicle vehicle) {
        repository.save(mapper.map(vehicle, VehicleEntity.class));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Vehicle vehicle) {
        VehicleEntity existingEntity = repository.findById(vehicle.getVehicleID())
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
        VehicleEntity updatedEntity = mapper.map(vehicle, VehicleEntity.class);
        updatedEntity.setVehicleID(existingEntity.getVehicleID());
        repository.save(updatedEntity);
    }

    @Override
    public List<Vehicle> searchByName(String brand) {
        List<VehicleEntity> entity = (List<VehicleEntity>) repository.findByBrand(brand);
        List<Vehicle> list = new ArrayList<>();
        entity.forEach(entityies -> list.add(mapper.map(entityies,Vehicle.class)));
        return list;
    }

    @Override
    public List<Vehicle> getAll() {
        List<VehicleEntity> entity = repository.findAll();
        List<Vehicle> arrayList = new ArrayList<>();
        entity.forEach(e -> mapper.map(e, VehicleEntity.class));
        return arrayList;
    }
}
