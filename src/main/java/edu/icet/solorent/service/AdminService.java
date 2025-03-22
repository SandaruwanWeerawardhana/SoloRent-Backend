package edu.icet.solorent.service;

import edu.icet.solorent.dto.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void add(Admin admin);

    void delete(Long id);

    void update(Admin admin);

    Admin searchById(Long id);

    List<Admin> getAll();
}
