package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/solorent/booking")
@CrossOrigin
public class BookingController {
    final BookingService bookingService;

    @PostMapping("/add")
    public void add(@RequestBody Booking booking) {
        bookingService.add(booking);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Booking booking) {
        bookingService.update(booking);
    }

    @GetMapping("/get-all")
    public List<Booking> getAll() {
        return bookingService.getAll();
    }
}
