package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Review;
import edu.icet.solorent.entity.ReviewEntity;
import edu.icet.solorent.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ReviewServiceImpl service;

    private Review sampleReviewDto;
    private ReviewEntity sampleReviewEntity;

    @BeforeEach
    void setUp() {
        Date testDate = new Date();

        sampleReviewDto = new Review();
        sampleReviewDto.setReviewID(1L);
        sampleReviewDto.setUserID(100L);
        sampleReviewDto.setVehicleID(200L);
        sampleReviewDto.setComment("Excellent vehicle! Very comfortable and well-maintained.");
        sampleReviewDto.setDatePosted(testDate);

        sampleReviewEntity = new ReviewEntity();
        sampleReviewEntity.setReviewID(1L);
        sampleReviewEntity.setUserID(100L);
        sampleReviewEntity.setVehicleID(200L);
        sampleReviewEntity.setComment("Excellent vehicle! Very comfortable and well-maintained.");
        sampleReviewEntity.setDatePosted(testDate);
    }

    @Test
    void add_shouldMapAndSaveReview() {
        // Arrange
        when(mapper.map(sampleReviewDto, ReviewEntity.class)).thenReturn(sampleReviewEntity);

        // Act
        service.add(sampleReviewDto);

        // Assert
        verify(mapper, times(1)).map(sampleReviewDto, ReviewEntity.class);
        verify(repository, times(1)).save(sampleReviewEntity);
    }

    @Test
    void add_shouldCallRepositorySaveWithMappedEntity() {
        // Arrange
        Review dto = new Review();
        dto.setUserID(150L);
        dto.setVehicleID(250L);
        dto.setComment("Good service, but could be better.");
        dto.setDatePosted(new Date());

        ReviewEntity mappedEntity = new ReviewEntity();
        mappedEntity.setUserID(150L);
        mappedEntity.setVehicleID(250L);
        mappedEntity.setComment("Good service, but could be better.");
        mappedEntity.setDatePosted(new Date());

        when(mapper.map(dto, ReviewEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(dto);

        // Assert
        ArgumentCaptor<ReviewEntity> captor = ArgumentCaptor.forClass(ReviewEntity.class);
        verify(repository, times(1)).save(captor.capture());

        ReviewEntity saved = captor.getValue();
        assertEquals(150L, saved.getUserID());
        assertEquals(250L, saved.getVehicleID());
        assertEquals("Good service, but could be better.", saved.getComment());
    }

    @Test
    void add_withLongComment_shouldSaveCorrectly() {
        // Arrange
        String longComment = "This is an amazing car rental service. The vehicle was in pristine condition, " +
                "the staff was very professional, and the entire process was smooth. I highly recommend this service!";

        Review dto = new Review();
        dto.setUserID(200L);
        dto.setVehicleID(300L);
        dto.setComment(longComment);
        dto.setDatePosted(new Date());

        ReviewEntity mappedEntity = new ReviewEntity();
        mappedEntity.setComment(longComment);

        when(mapper.map(dto, ReviewEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(dto);

        // Assert
        ArgumentCaptor<ReviewEntity> captor = ArgumentCaptor.forClass(ReviewEntity.class);
        verify(repository, times(1)).save(captor.capture());
        assertEquals(longComment, captor.getValue().getComment());
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        // Act
        service.delete(5L);

        // Assert
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void delete_withDifferentId_shouldCallRepositoryWithCorrectId() {
        // Act
        service.delete(999L);

        // Assert
        verify(repository, times(1)).deleteById(999L);
        verify(repository, never()).deleteById(5L);
    }

    @Test
    void delete_withNullId_shouldStillCallRepository() {
        // Act
        service.delete(null);

        // Assert
        verify(repository, times(1)).deleteById(null);
    }

    @Test
    void update_shouldMapAndSaveReview() {
        // Arrange
        Review updatedDto = new Review();
        updatedDto.setReviewID(1L);
        updatedDto.setUserID(100L);
        updatedDto.setVehicleID(200L);
        updatedDto.setComment("Updated: Great experience overall!");
        updatedDto.setDatePosted(new Date());

        ReviewEntity mappedEntity = new ReviewEntity();
        mappedEntity.setReviewID(1L);
        mappedEntity.setUserID(100L);
        mappedEntity.setVehicleID(200L);
        mappedEntity.setComment("Updated: Great experience overall!");
        mappedEntity.setDatePosted(new Date());

        when(mapper.map(updatedDto, ReviewEntity.class)).thenReturn(mappedEntity);

        // Act
        service.update(updatedDto);

        // Assert
        verify(mapper, times(1)).map(updatedDto, ReviewEntity.class);
        verify(repository, times(1)).save(mappedEntity);
    }

    @Test
    void update_shouldSaveWithUpdatedFields() {
        // Arrange
        when(mapper.map(sampleReviewDto, ReviewEntity.class)).thenReturn(sampleReviewEntity);

        // Act
        service.update(sampleReviewDto);

        // Assert
        ArgumentCaptor<ReviewEntity> captor = ArgumentCaptor.forClass(ReviewEntity.class);
        verify(repository, times(1)).save(captor.capture());

        ReviewEntity saved = captor.getValue();
        assertEquals(sampleReviewEntity.getReviewID(), saved.getReviewID());
        assertEquals(sampleReviewEntity.getUserID(), saved.getUserID());
        assertEquals(sampleReviewEntity.getVehicleID(), saved.getVehicleID());
        assertEquals(sampleReviewEntity.getComment(), saved.getComment());
    }

    @Test
    void update_changingComment_shouldUpdateCorrectly() {
        // Arrange
        Review dto = new Review();
        dto.setReviewID(1L);
        dto.setComment("Edited: Amazing service!");

        ReviewEntity entity = new ReviewEntity();
        entity.setReviewID(1L);
        entity.setComment("Edited: Amazing service!");

        when(mapper.map(dto, ReviewEntity.class)).thenReturn(entity);

        // Act
        service.update(dto);

        // Assert
        ArgumentCaptor<ReviewEntity> captor = ArgumentCaptor.forClass(ReviewEntity.class);
        verify(repository).save(captor.capture());
        assertEquals("Edited: Amazing service!", captor.getValue().getComment());
    }

    @Test
    void searchById_whenFound_shouldReturnOptionalWithEntity() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(sampleReviewEntity));

        // Act
        Optional<ReviewEntity> result = service.searchById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleReviewEntity, result.get());
        assertEquals(100L, result.get().getUserID());
        assertEquals(200L, result.get().getVehicleID());
        assertEquals("Excellent vehicle! Very comfortable and well-maintained.", result.get().getComment());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void searchById_whenNotFound_shouldReturnEmptyOptional() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<ReviewEntity> result = service.searchById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void searchById_withNullId_shouldCallRepository() {
        // Arrange
        when(repository.findById(null)).thenReturn(Optional.empty());

        // Act
        Optional<ReviewEntity> result = service.searchById(null);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(null);
    }

    @Test
    void getAll_shouldReturnListOfMappedReviews() {
        // Arrange
        ReviewEntity entity1 = new ReviewEntity();
        entity1.setReviewID(1L);
        entity1.setUserID(100L);
        entity1.setVehicleID(200L);
        entity1.setComment("Great service!");
        entity1.setDatePosted(new Date());

        ReviewEntity entity2 = new ReviewEntity();
        entity2.setReviewID(2L);
        entity2.setUserID(101L);
        entity2.setVehicleID(201L);
        entity2.setComment("Highly recommended!");
        entity2.setDatePosted(new Date());

        List<ReviewEntity> entities = List.of(entity1, entity2);

        Review dto1 = new Review();
        dto1.setReviewID(1L);
        dto1.setUserID(100L);
        dto1.setVehicleID(200L);
        dto1.setComment("Great service!");
        dto1.setDatePosted(new Date());

        Review dto2 = new Review();
        dto2.setReviewID(2L);
        dto2.setUserID(101L);
        dto2.setVehicleID(201L);
        dto2.setComment("Highly recommended!");
        dto2.setDatePosted(new Date());

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Review.class)).thenReturn(dto1);
        when(mapper.map(entity2, Review.class)).thenReturn(dto2);

        // Act
        List<Review> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals("Great service!", result.get(0).getComment());
        assertEquals("Highly recommended!", result.get(1).getComment());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(entity1, Review.class);
        verify(mapper, times(1)).map(entity2, Review.class);
    }

    @Test
    void getAll_whenNoReviews_shouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Review> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).map(any(ReviewEntity.class), eq(Review.class));
    }

    @Test
    void getAll_withMultipleReviews_shouldMapAllCorrectly() {
        // Arrange
        List<ReviewEntity> entities = new ArrayList<>();
        List<Review> expectedDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            ReviewEntity entity = new ReviewEntity();
            entity.setReviewID((long) i);
            entity.setUserID((long) (100 + i));
            entity.setVehicleID((long) (200 + i));
            entity.setComment("Review comment " + i);
            entity.setDatePosted(new Date());
            entities.add(entity);

            Review dto = new Review();
            dto.setReviewID((long) i);
            dto.setUserID((long) (100 + i));
            dto.setVehicleID((long) (200 + i));
            dto.setComment("Review comment " + i);
            dto.setDatePosted(new Date());
            expectedDtos.add(dto);

            when(mapper.map(entity, Review.class)).thenReturn(dto);
        }

        when(repository.findAll()).thenReturn(entities);

        // Act
        List<Review> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDtos.get(i).getReviewID(), result.get(i).getReviewID());
            assertEquals(expectedDtos.get(i).getUserID(), result.get(i).getUserID());
            assertEquals(expectedDtos.get(i).getVehicleID(), result.get(i).getVehicleID());
            assertEquals(expectedDtos.get(i).getComment(), result.get(i).getComment());
        }
        verify(repository, times(1)).findAll();
        verify(mapper, times(5)).map(any(ReviewEntity.class), eq(Review.class));
    }

    @Test
    void getAll_shouldPreserveOrderFromRepository() {
        // Arrange
        ReviewEntity entity1 = new ReviewEntity();
        entity1.setReviewID(3L);
        entity1.setComment("Third review");

        ReviewEntity entity2 = new ReviewEntity();
        entity2.setReviewID(1L);
        entity2.setComment("First review");

        ReviewEntity entity3 = new ReviewEntity();
        entity3.setReviewID(2L);
        entity3.setComment("Second review");

        List<ReviewEntity> entities = List.of(entity1, entity2, entity3);

        Review dto1 = new Review();
        dto1.setReviewID(3L);
        dto1.setComment("Third review");

        Review dto2 = new Review();
        dto2.setReviewID(1L);
        dto2.setComment("First review");

        Review dto3 = new Review();
        dto3.setReviewID(2L);
        dto3.setComment("Second review");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Review.class)).thenReturn(dto1);
        when(mapper.map(entity2, Review.class)).thenReturn(dto2);
        when(mapper.map(entity3, Review.class)).thenReturn(dto3);

        // Act
        List<Review> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(3L, result.get(0).getReviewID());
        assertEquals(1L, result.get(1).getReviewID());
        assertEquals(2L, result.get(2).getReviewID());
        assertEquals("Third review", result.get(0).getComment());
        assertEquals("First review", result.get(1).getComment());
        assertEquals("Second review", result.get(2).getComment());
    }

    @Test
    void getAll_withDifferentUserAndVehicleCombinations_shouldReturnAll() {
        // Arrange
        ReviewEntity entity1 = new ReviewEntity();
        entity1.setReviewID(1L);
        entity1.setUserID(100L);
        entity1.setVehicleID(200L);

        ReviewEntity entity2 = new ReviewEntity();
        entity2.setReviewID(2L);
        entity2.setUserID(100L);
        entity2.setVehicleID(201L);

        ReviewEntity entity3 = new ReviewEntity();
        entity3.setReviewID(3L);
        entity3.setUserID(101L);
        entity3.setVehicleID(200L);

        List<ReviewEntity> entities = List.of(entity1, entity2, entity3);

        Review dto1 = new Review();
        dto1.setReviewID(1L);
        dto1.setUserID(100L);
        dto1.setVehicleID(200L);

        Review dto2 = new Review();
        dto2.setReviewID(2L);
        dto2.setUserID(100L);
        dto2.setVehicleID(201L);

        Review dto3 = new Review();
        dto3.setReviewID(3L);
        dto3.setUserID(101L);
        dto3.setVehicleID(200L);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Review.class)).thenReturn(dto1);
        when(mapper.map(entity2, Review.class)).thenReturn(dto2);
        when(mapper.map(entity3, Review.class)).thenReturn(dto3);

        // Act
        List<Review> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        // Same user, different vehicles
        assertEquals(100L, result.get(0).getUserID());
        assertEquals(200L, result.get(0).getVehicleID());
        assertEquals(100L, result.get(1).getUserID());
        assertEquals(201L, result.get(1).getVehicleID());
        // Different user, same vehicle as first
        assertEquals(101L, result.get(2).getUserID());
        assertEquals(200L, result.get(2).getVehicleID());
    }
}