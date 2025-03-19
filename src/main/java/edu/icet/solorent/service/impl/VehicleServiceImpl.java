package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Vehicle;
import edu.icet.solorent.entity.VehicleEntity;
import edu.icet.solorent.repository.VehicleRepository;
import edu.icet.solorent.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        repository.save(mapper.map(vehicle, VehicleEntity.class));
    }

    @Override
    public Optional<VehicleEntity> searchById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Vehicle> getAll() {
        List<VehicleEntity> entity = repository.findAll();
        List<Vehicle> arrayList = new ArrayList<>();
        entity.forEach(e -> mapper.map(e, VehicleEntity.class));
        return arrayList;
    }
}
