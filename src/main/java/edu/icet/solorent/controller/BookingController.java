package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/booking")
@CrossOrigin
public class BookingController {
    final BookingService bookingService;

    public void add(Booking booking) {
    }

    public void delete(Long id) {
    }

    public void update(Booking booking) {
    }

    public List<Booking> getAll() {
        return List.of();
    }
}
