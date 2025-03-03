package edu.icet.solorent.service;

import edu.icet.solorent.dto.Review;

import java.util.List;

public interface ReviewService {
    void add(Review review);

    void delete(Long id);

    void update(Review review);

    Review searchById(Long id);

    List<Review> getAll();
}
