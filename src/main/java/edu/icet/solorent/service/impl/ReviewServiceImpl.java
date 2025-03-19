package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Review;
import edu.icet.solorent.entity.ReviewEntity;
import edu.icet.solorent.repository.ReviewRepository;
import edu.icet.solorent.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ModelMapper mapper;

    @Override
    public void add(Review review) {
        repository.save(mapper.map(review, ReviewEntity.class));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Review review) {
        repository.save(mapper.map(review, ReviewEntity.class));
    }

    @Override
    public Optional<ReviewEntity> searchById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Review> getAll() {
        List<ReviewEntity> entity = repository.findAll();
        List<Review> arrayList = new ArrayList<>();
        entity.forEach(e -> mapper.map(e, ReviewEntity.class));
        return arrayList;
    }
}
