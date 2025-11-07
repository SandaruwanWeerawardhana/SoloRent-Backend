package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Booking;
import edu.icet.solorent.entity.BookingEntity;
import edu.icet.solorent.repository.BookingRepository;
import edu.icet.solorent.util.BookingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private BookingServiceImpl service;

    private Booking sampleBookingDto;
    private BookingEntity sampleBookingEntity;

    @BeforeEach
    void setUp() {
        sampleBookingDto = new Booking();
        sampleBookingDto.setBookingID(1L);
        sampleBookingDto.setCustomerName("John Doe");
        sampleBookingDto.setEmail("john@example.com");
        sampleBookingDto.setContact("0771234567");
        sampleBookingDto.setVehicleID(100L);
        sampleBookingDto.setStartDate(LocalDate.of(2025, 11, 10));
        sampleBookingDto.setEndDate(LocalDate.of(2025, 11, 15));
        sampleBookingDto.setPickupTime("10:00 AM");
        sampleBookingDto.setPickupLocation("Colombo");
        sampleBookingDto.setReturnLocation("Kandy");
        sampleBookingDto.setDescription("Business trip");
        sampleBookingDto.setTotalPrice(15000.0);
        sampleBookingDto.setBookingStatus(BookingStatus.PENDING);

        sampleBookingEntity = new BookingEntity();
        sampleBookingEntity.setBookingID(1L);
        sampleBookingEntity.setCustomerName("John Doe");
        sampleBookingEntity.setEmail("john@example.com");
        sampleBookingEntity.setContact("0771234567");
        sampleBookingEntity.setVehicleID(100L);
        sampleBookingEntity.setStartDate(LocalDate.of(2025, 11, 10));
        sampleBookingEntity.setEndDate(LocalDate.of(2025, 11, 15));
        sampleBookingEntity.setPickupTime("10:00 AM");
        sampleBookingEntity.setPickupLocation("Colombo");
        sampleBookingEntity.setReturnLocation("Kandy");
        sampleBookingEntity.setDescription("Business trip");
        sampleBookingEntity.setBookingDateTime(new Date());
        sampleBookingEntity.setTotalPrice(15000.0);
        sampleBookingEntity.setBookingStatus(BookingStatus.PENDING);
    }

    @Test
    void add_shouldMapAndSaveBookingWithBookingDateTime() {
        // Arrange
        BookingEntity mappedEntity = new BookingEntity();
        mappedEntity.setCustomerName("John Doe");
        mappedEntity.setEmail("john@example.com");
        mappedEntity.setContact("0771234567");
        mappedEntity.setVehicleID(100L);
        mappedEntity.setStartDate(LocalDate.of(2025, 11, 10));
        mappedEntity.setEndDate(LocalDate.of(2025, 11, 15));
        mappedEntity.setPickupTime("10:00 AM");
        mappedEntity.setPickupLocation("Colombo");
        mappedEntity.setReturnLocation("Kandy");

        when(mapper.map(sampleBookingDto, BookingEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(sampleBookingDto);

        // Assert
        ArgumentCaptor<BookingEntity> captor = ArgumentCaptor.forClass(BookingEntity.class);
        verify(repository, times(1)).save(captor.capture());

        BookingEntity savedEntity = captor.getValue();
        assertNotNull(savedEntity.getBookingDateTime(), "Booking date time should be set");
        assertEquals("John Doe", savedEntity.getCustomerName());
        assertEquals("john@example.com", savedEntity.getEmail());
        verify(mapper, times(1)).map(sampleBookingDto, BookingEntity.class);
    }

    @Test
    void add_shouldSetCurrentDateTimeForBooking() {
        // Arrange
        BookingEntity mappedEntity = new BookingEntity();
        when(mapper.map(any(Booking.class), eq(BookingEntity.class))).thenReturn(mappedEntity);

        Date beforeCall = new Date();

        // Act
        service.add(sampleBookingDto);

        // Assert
        ArgumentCaptor<BookingEntity> captor = ArgumentCaptor.forClass(BookingEntity.class);
        verify(repository).save(captor.capture());

        Date afterCall = new Date();
        BookingEntity saved = captor.getValue();

        assertNotNull(saved.getBookingDateTime());
        assertTrue(saved.getBookingDateTime().getTime() >= beforeCall.getTime());
        assertTrue(saved.getBookingDateTime().getTime() <= afterCall.getTime());
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        // Act
        service.delete(5L);

        // Assert
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void delete_withNullId_shouldStillCallRepository() {
        // Act
        service.delete(null);

        // Assert
        verify(repository, times(1)).deleteById(null);
    }

    @Test
    void update_shouldMapAndSaveBooking() {
        // Arrange
        Booking updatedBooking = new Booking();
        updatedBooking.setBookingID(1L);
        updatedBooking.setCustomerName("Jane Doe");
        updatedBooking.setEmail("jane@example.com");
        updatedBooking.setContact("0779876543");
        updatedBooking.setBookingStatus(BookingStatus.CONFIRMED);

        BookingEntity mappedEntity = new BookingEntity();
        mappedEntity.setBookingID(1L);
        mappedEntity.setCustomerName("Jane Doe");
        mappedEntity.setEmail("jane@example.com");
        mappedEntity.setContact("0779876543");
        mappedEntity.setBookingStatus(BookingStatus.CONFIRMED);

        when(mapper.map(updatedBooking, BookingEntity.class)).thenReturn(mappedEntity);

        // Act
        service.update(updatedBooking);

        // Assert
        verify(mapper, times(1)).map(updatedBooking, BookingEntity.class);
        verify(repository, times(1)).save(mappedEntity);
    }

    @Test
    void update_withAllFields_shouldSaveCorrectly() {
        // Arrange
        when(mapper.map(sampleBookingDto, BookingEntity.class)).thenReturn(sampleBookingEntity);

        // Act
        service.update(sampleBookingDto);

        // Assert
        ArgumentCaptor<BookingEntity> captor = ArgumentCaptor.forClass(BookingEntity.class);
        verify(repository, times(1)).save(captor.capture());

        BookingEntity saved = captor.getValue();
        assertEquals(sampleBookingEntity.getBookingID(), saved.getBookingID());
        assertEquals(sampleBookingEntity.getCustomerName(), saved.getCustomerName());
        assertEquals(sampleBookingEntity.getBookingStatus(), saved.getBookingStatus());
    }

    @Test
    void searchById_whenFound_shouldReturnOptionalWithEntity() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(sampleBookingEntity));

        // Act
        Optional<BookingEntity> result = service.searchById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleBookingEntity, result.get());
        assertEquals("John Doe", result.get().getCustomerName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void searchById_whenNotFound_shouldReturnEmptyOptional() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<BookingEntity> result = service.searchById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void searchById_withNullId_shouldCallRepository() {
        // Arrange
        when(repository.findById(null)).thenReturn(Optional.empty());

        // Act
        Optional<BookingEntity> result = service.searchById(null);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(null);
    }

    @Test
    void getAll_shouldReturnListOfMappedBookings() {
        // Arrange
        BookingEntity entity1 = new BookingEntity();
        entity1.setBookingID(1L);
        entity1.setCustomerName("Customer 1");
        entity1.setEmail("customer1@example.com");
        entity1.setContact("0771111111");

        BookingEntity entity2 = new BookingEntity();
        entity2.setBookingID(2L);
        entity2.setCustomerName("Customer 2");
        entity2.setEmail("customer2@example.com");
        entity2.setContact("0772222222");

        List<BookingEntity> entities = List.of(entity1, entity2);

        Booking dto1 = new Booking();
        dto1.setBookingID(1L);
        dto1.setCustomerName("Customer 1");
        dto1.setEmail("customer1@example.com");
        dto1.setContact("0771111111");

        Booking dto2 = new Booking();
        dto2.setBookingID(2L);
        dto2.setCustomerName("Customer 2");
        dto2.setEmail("customer2@example.com");
        dto2.setContact("0772222222");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Booking.class)).thenReturn(dto1);
        when(mapper.map(entity2, Booking.class)).thenReturn(dto2);

        // Act
        List<Booking> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals("Customer 1", result.get(0).getCustomerName());
        assertEquals("Customer 2", result.get(1).getCustomerName());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(entity1, Booking.class);
        verify(mapper, times(1)).map(entity2, Booking.class);
    }

    @Test
    void getAll_whenNoBookings_shouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Booking> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).map(any(BookingEntity.class), eq(Booking.class));
    }

    @Test
    void getAll_withMultipleBookings_shouldMapAllCorrectly() {
        // Arrange
        List<BookingEntity> entities = new ArrayList<>();
        List<Booking> expectedDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            BookingEntity entity = new BookingEntity();
            entity.setBookingID((long) i);
            entity.setCustomerName("Customer " + i);
            entities.add(entity);

            Booking dto = new Booking();
            dto.setBookingID((long) i);
            dto.setCustomerName("Customer " + i);
            expectedDtos.add(dto);

            when(mapper.map(entity, Booking.class)).thenReturn(dto);
        }

        when(repository.findAll()).thenReturn(entities);

        // Act
        List<Booking> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDtos.get(i).getBookingID(), result.get(i).getBookingID());
            assertEquals(expectedDtos.get(i).getCustomerName(), result.get(i).getCustomerName());
        }
        verify(repository, times(1)).findAll();
        verify(mapper, times(5)).map(any(BookingEntity.class), eq(Booking.class));
    }
}