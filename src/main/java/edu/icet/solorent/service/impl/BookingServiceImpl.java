package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.entity.BookingEntity;
import edu.icet.solorent.repository.BookingRepository;
import edu.icet.solorent.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;
    private final ModelMapper mapper;

    @Override
    public void add(Booking booking) {
        repository.save(mapper.map(booking, BookingEntity.class));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Booking booking) {
        repository.save(mapper.map(booking, BookingEntity.class));
    }

    @Override
    public Optional<BookingEntity> searchById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Booking> getAll() {
        List<BookingEntity> entity = repository.findAll();
        List<Booking> arrayList = new ArrayList<>();
        entity.forEach(e -> mapper.map(e, BookingEntity.class));
        return arrayList;
    }
}
