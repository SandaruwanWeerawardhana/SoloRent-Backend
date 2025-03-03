package edu.icet.solorent.service;

import edu.icet.solorent.dto.Maintenance;

import java.util.List;

public interface MaintetanceService {
    void add(Maintenance maintenance);

    void delete(Long id);

    void update(Maintenance maintenance);

    Maintenance searchById(Long id);

    List<Maintenance> getAll();
}
