package edu.icet.solorent.service;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.entity.BookingEntity;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    void add(Booking booking);

    void delete(Long id);

    void update(Booking booking);

    Optional<BookingEntity> searchById(Long id);

    List<Booking> getAll();
}
