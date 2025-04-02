package edu.icet.solorent.service;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.entity.AdminEntity;

import java.util.List;

public interface AdminService {
    AdminEntity add(Admin admin);

    void delete(Long id);

    void update(Admin admin);

    Admin searchById(Long id);

    List<Admin> getAll();
}
