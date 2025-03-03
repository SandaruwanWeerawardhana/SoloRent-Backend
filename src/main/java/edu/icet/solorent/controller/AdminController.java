package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.dto.User;
import edu.icet.solorent.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    final AdminService adminService;

    public void add(Admin admin) {
    }

    public void delete(Long id) {
    }

    public void update(Admin admin) {
    }

    public List<Admin> getAll() {
        return List.of();
    }

}
