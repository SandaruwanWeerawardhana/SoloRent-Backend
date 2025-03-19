package edu.icet.solorent.service;

import edu.icet.solorent.dto.Review;
import edu.icet.solorent.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    void add(Review review);

    void delete(Long id);

    void update(Review review);

    Optional<ReviewEntity> searchById(Long id);

    List<Review> getAll();
}
