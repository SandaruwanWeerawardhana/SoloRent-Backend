package edu.icet.solorent.controller;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/solorent/booking")
@CrossOrigin
public class BookingController {
    final BookingService bookingService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody Booking booking) {
        bookingService.add(booking);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@Valid @RequestBody Booking booking) {
        bookingService.update(booking);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/get-all")
    public List<Booking> getAll() {
        return bookingService.getAll();
    }
}
