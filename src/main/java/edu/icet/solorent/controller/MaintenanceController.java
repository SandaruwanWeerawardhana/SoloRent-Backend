package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.dto.Maintenance;
import edu.icet.solorent.service.BookingService;
import edu.icet.solorent.service.MaintetanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MaintenanceController {
    final MaintetanceService maintetanceService;

    public void add(Maintenance maintenance) {
    }

    public void delete(Long id) {
    }

    public void update(Maintenance maintenance) {
    }

    public List<Maintenance> getAll() {
        return List.of();
    }

}
