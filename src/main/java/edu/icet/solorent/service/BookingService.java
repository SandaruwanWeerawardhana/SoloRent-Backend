package edu.icet.solorent.service;

import edu.icet.solorent.dto.Booking;

import java.util.List;

public interface BookingService {
    void add(Booking booking);

    void delete(Long id);

    void update(Booking booking);

    Booking searchById(Long id);

    List<Booking> getAll();
}
