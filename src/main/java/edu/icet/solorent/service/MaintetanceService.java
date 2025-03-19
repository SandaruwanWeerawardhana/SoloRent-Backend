package edu.icet.solorent.service;

import edu.icet.solorent.dto.Maintenance;
import edu.icet.solorent.entity.MaintenanceEntity;

import java.util.List;
import java.util.Optional;

public interface MaintetanceService {
    void add(Maintenance maintenance);

    void delete(Long id);

    void update(Maintenance maintenance);

    Optional<MaintenanceEntity> searchById(Long id);

    List<Maintenance> getAll();
}
