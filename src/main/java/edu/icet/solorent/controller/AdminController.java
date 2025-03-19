package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/solorent/admin")
@CrossOrigin
public class AdminController {
    final AdminService adminService;

    @PostMapping("/add")
    public void add(@RequestBody Admin admin) {
        adminService.add(admin);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        adminService.delete(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Admin admin) {
        adminService.update(admin);
    }

    @GetMapping("/get-all")
    public List<Admin> getAll() {
        return adminService.getAll();
    }

}
