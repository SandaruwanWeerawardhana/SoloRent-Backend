package edu.icet.solorent.service;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.entity.AdminEntity;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void add(Admin admin);

    void delete(Long id);

    void update(Admin admin);

    Optional<AdminEntity> searchById(Long id);

    List<Admin> getAll();
}
