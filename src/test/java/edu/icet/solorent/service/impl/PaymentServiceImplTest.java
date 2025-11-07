package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Payment;
import edu.icet.solorent.entity.PaymentEntity;
import edu.icet.solorent.repository.PaymentRepository;
import edu.icet.solorent.util.PaymentMethod;
import edu.icet.solorent.util.PaymentStatus;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PaymentServiceImpl service;

    private Payment samplePaymentDto;
    private PaymentEntity samplePaymentEntity;

    @BeforeEach
    void setUp() {
        samplePaymentDto = new Payment();
        samplePaymentDto.setPaymentID(1L);
        samplePaymentDto.setBookingID(100L);
        samplePaymentDto.setAmount(25000.0);
        samplePaymentDto.setPaymentDate(LocalDate.of(2025, 11, 7));
        samplePaymentDto.setMethod(PaymentMethod.CARD);
        samplePaymentDto.setStatus(PaymentStatus.SUCCESS);

        samplePaymentEntity = new PaymentEntity();
        samplePaymentEntity.setPaymentID(1L);
        samplePaymentEntity.setBookingID(100L);
        samplePaymentEntity.setAmount(25000.0);
        samplePaymentEntity.setPaymentDate(LocalDate.of(2025, 11, 7));
        samplePaymentEntity.setMethod(PaymentMethod.CARD);
        samplePaymentEntity.setStatus(PaymentStatus.SUCCESS);
    }

    @Test
    void add_shouldMapAndSavePayment() {
        // Arrange
        when(mapper.map(samplePaymentDto, PaymentEntity.class)).thenReturn(samplePaymentEntity);

        // Act
        service.add(samplePaymentDto);

        // Assert
        verify(mapper, times(1)).map(samplePaymentDto, PaymentEntity.class);
        verify(repository, times(1)).save(samplePaymentEntity);
    }

    @Test
    void add_shouldCallRepositorySaveWithMappedEntity() {
        // Arrange
        Payment dto = new Payment();
        dto.setBookingID(200L);
        dto.setAmount(15000.0);
        dto.setPaymentDate(LocalDate.of(2025, 12, 1));
        dto.setMethod(PaymentMethod.CARD);
        dto.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity mappedEntity = new PaymentEntity();
        mappedEntity.setBookingID(200L);
        mappedEntity.setAmount(15000.0);
        mappedEntity.setPaymentDate(LocalDate.of(2025, 12, 1));
        mappedEntity.setMethod(PaymentMethod.CARD);
        mappedEntity.setStatus(PaymentStatus.SUCCESS);

        when(mapper.map(dto, PaymentEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(dto);

        // Assert
        ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);
        verify(repository, times(1)).save(captor.capture());

        PaymentEntity saved = captor.getValue();
        assertEquals(200L, saved.getBookingID());
        assertEquals(15000.0, saved.getAmount());
        assertEquals(PaymentMethod.CARD, saved.getMethod());
    }

    @Test
    void add_withDifferentPaymentMethods_shouldSaveCorrectly() {
        // Arrange
        Payment cardPayment = new Payment();
        cardPayment.setBookingID(300L);
        cardPayment.setAmount(30000.0);
        cardPayment.setMethod(PaymentMethod.CARD);
        cardPayment.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity mappedEntity = new PaymentEntity();
        mappedEntity.setMethod(PaymentMethod.CARD);

        when(mapper.map(cardPayment, PaymentEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(cardPayment);

        // Assert
        verify(repository, times(1)).save(mappedEntity);
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
    void update_shouldMapAndSavePayment() {
        // Arrange
        Payment updatedDto = new Payment();
        updatedDto.setPaymentID(1L);
        updatedDto.setBookingID(100L);
        updatedDto.setAmount(28000.0);
        updatedDto.setPaymentDate(LocalDate.of(2025, 11, 8));
        updatedDto.setMethod(PaymentMethod.CARD);
        updatedDto.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity mappedEntity = new PaymentEntity();
        mappedEntity.setPaymentID(1L);
        mappedEntity.setBookingID(100L);
        mappedEntity.setAmount(28000.0);
        mappedEntity.setPaymentDate(LocalDate.of(2025, 11, 8));
        mappedEntity.setMethod(PaymentMethod.CARD);
        mappedEntity.setStatus(PaymentStatus.SUCCESS);

        when(mapper.map(updatedDto, PaymentEntity.class)).thenReturn(mappedEntity);

        // Act
        service.update(updatedDto);

        // Assert
        verify(mapper, times(1)).map(updatedDto, PaymentEntity.class);
        verify(repository, times(1)).save(mappedEntity);
    }

    @Test
    void update_shouldSaveWithUpdatedFields() {
        // Arrange
        when(mapper.map(samplePaymentDto, PaymentEntity.class)).thenReturn(samplePaymentEntity);

        // Act
        service.update(samplePaymentDto);

        // Assert
        ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);
        verify(repository, times(1)).save(captor.capture());

        PaymentEntity saved = captor.getValue();
        assertEquals(samplePaymentEntity.getPaymentID(), saved.getPaymentID());
        assertEquals(samplePaymentEntity.getBookingID(), saved.getBookingID());
        assertEquals(samplePaymentEntity.getAmount(), saved.getAmount());
        assertEquals(samplePaymentEntity.getStatus(), saved.getStatus());
    }

    @Test
    void update_changingPaymentStatus_shouldUpdateCorrectly() {
        // Arrange
        Payment dto = new Payment();
        dto.setPaymentID(1L);
        dto.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity entity = new PaymentEntity();
        entity.setPaymentID(1L);
        entity.setStatus(PaymentStatus.SUCCESS);

        when(mapper.map(dto, PaymentEntity.class)).thenReturn(entity);

        // Act
        service.update(dto);

        // Assert
        ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);
        verify(repository).save(captor.capture());
        assertEquals(PaymentStatus.SUCCESS, captor.getValue().getStatus());
    }

    @Test
    void searchById_whenFound_shouldReturnOptionalWithEntity() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(samplePaymentEntity));

        // Act
        Optional<PaymentEntity> result = service.searchById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(samplePaymentEntity, result.get());
        assertEquals(100L, result.get().getBookingID());
        assertEquals(25000.0, result.get().getAmount());
        assertEquals(PaymentMethod.CARD, result.get().getMethod());
        assertEquals(PaymentStatus.SUCCESS, result.get().getStatus());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void searchById_whenNotFound_shouldReturnEmptyOptional() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<PaymentEntity> result = service.searchById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void searchById_withNullId_shouldCallRepository() {
        // Arrange
        when(repository.findById(null)).thenReturn(Optional.empty());

        // Act
        Optional<PaymentEntity> result = service.searchById(null);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(null);
    }

    @Test
    void getAll_shouldReturnListOfMappedPayments() {
        // Arrange
        PaymentEntity entity1 = new PaymentEntity();
        entity1.setPaymentID(1L);
        entity1.setBookingID(100L);
        entity1.setAmount(10000.0);
        entity1.setPaymentDate(LocalDate.of(2025, 11, 1));
        entity1.setMethod(PaymentMethod.CARD);
        entity1.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity entity2 = new PaymentEntity();
        entity2.setPaymentID(2L);
        entity2.setBookingID(200L);
        entity2.setAmount(20000.0);
        entity2.setPaymentDate(LocalDate.of(2025, 11, 2));
        entity2.setMethod(PaymentMethod.CARD);
        entity2.setStatus(PaymentStatus.SUCCESS);

        List<PaymentEntity> entities = List.of(entity1, entity2);

        Payment dto1 = new Payment();
        dto1.setPaymentID(1L);
        dto1.setBookingID(100L);
        dto1.setAmount(10000.0);
        dto1.setPaymentDate(LocalDate.of(2025, 11, 1));
        dto1.setMethod(PaymentMethod.CARD);
        dto1.setStatus(PaymentStatus.SUCCESS);

        Payment dto2 = new Payment();
        dto2.setPaymentID(2L);
        dto2.setBookingID(200L);
        dto2.setAmount(20000.0);
        dto2.setPaymentDate(LocalDate.of(2025, 11, 2));
        dto2.setMethod(PaymentMethod.CARD);
        dto2.setStatus(PaymentStatus.SUCCESS);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Payment.class)).thenReturn(dto1);
        when(mapper.map(entity2, Payment.class)).thenReturn(dto2);

        // Act
        List<Payment> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals(100L, result.get(0).getBookingID());
        assertEquals(200L, result.get(1).getBookingID());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(entity1, Payment.class);
        verify(mapper, times(1)).map(entity2, Payment.class);
    }

    @Test
    void getAll_whenNoPayments_shouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Payment> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).map(any(PaymentEntity.class), eq(Payment.class));
    }

    @Test
    void getAll_withMultiplePayments_shouldMapAllCorrectly() {
        // Arrange
        List<PaymentEntity> entities = new ArrayList<>();
        List<Payment> expectedDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            PaymentEntity entity = new PaymentEntity();
            entity.setPaymentID((long) i);
            entity.setBookingID((long) (100 + i));
            entity.setAmount(5000.0 * i);
            entity.setPaymentDate(LocalDate.of(2025, 11, i));
            entity.setMethod(PaymentMethod.CARD);
            entity.setStatus(PaymentStatus.SUCCESS);
            entities.add(entity);

            Payment dto = new Payment();
            dto.setPaymentID((long) i);
            dto.setBookingID((long) (100 + i));
            dto.setAmount(5000.0 * i);
            dto.setPaymentDate(LocalDate.of(2025, 11, i));
            dto.setMethod(PaymentMethod.CARD);
            dto.setStatus(PaymentStatus.SUCCESS);
            expectedDtos.add(dto);

            when(mapper.map(entity, Payment.class)).thenReturn(dto);
        }

        when(repository.findAll()).thenReturn(entities);

        // Act
        List<Payment> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDtos.get(i).getPaymentID(), result.get(i).getPaymentID());
            assertEquals(expectedDtos.get(i).getBookingID(), result.get(i).getBookingID());
            assertEquals(expectedDtos.get(i).getAmount(), result.get(i).getAmount());
            assertEquals(expectedDtos.get(i).getMethod(), result.get(i).getMethod());
            assertEquals(expectedDtos.get(i).getStatus(), result.get(i).getStatus());
        }
        verify(repository, times(1)).findAll();
        verify(mapper, times(5)).map(any(PaymentEntity.class), eq(Payment.class));
    }

    @Test
    void getAll_shouldPreserveOrderFromRepository() {
        // Arrange
        PaymentEntity entity1 = new PaymentEntity();
        entity1.setPaymentID(3L);
        entity1.setBookingID(300L);

        PaymentEntity entity2 = new PaymentEntity();
        entity2.setPaymentID(1L);
        entity2.setBookingID(100L);

        PaymentEntity entity3 = new PaymentEntity();
        entity3.setPaymentID(2L);
        entity3.setBookingID(200L);

        List<PaymentEntity> entities = List.of(entity1, entity2, entity3);

        Payment dto1 = new Payment();
        dto1.setPaymentID(3L);
        dto1.setBookingID(300L);

        Payment dto2 = new Payment();
        dto2.setPaymentID(1L);
        dto2.setBookingID(100L);

        Payment dto3 = new Payment();
        dto3.setPaymentID(2L);
        dto3.setBookingID(200L);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Payment.class)).thenReturn(dto1);
        when(mapper.map(entity2, Payment.class)).thenReturn(dto2);
        when(mapper.map(entity3, Payment.class)).thenReturn(dto3);

        // Act
        List<Payment> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(3L, result.get(0).getPaymentID());
        assertEquals(1L, result.get(1).getPaymentID());
        assertEquals(2L, result.get(2).getPaymentID());
        assertEquals(300L, result.get(0).getBookingID());
        assertEquals(100L, result.get(1).getBookingID());
        assertEquals(200L, result.get(2).getBookingID());
    }

    @Test
    void getAll_withDifferentPaymentStatuses_shouldReturnAll() {
        // Arrange
        PaymentEntity completedEntity = new PaymentEntity();
        completedEntity.setPaymentID(1L);
        completedEntity.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity pendingEntity = new PaymentEntity();
        pendingEntity.setPaymentID(2L);
        pendingEntity.setStatus(PaymentStatus.SUCCESS);

        PaymentEntity refundedEntity = new PaymentEntity();
        refundedEntity.setPaymentID(3L);
        refundedEntity.setStatus(PaymentStatus.SUCCESS);

        List<PaymentEntity> entities = List.of(completedEntity, pendingEntity, refundedEntity);

        Payment completedDto = new Payment();
        completedDto.setPaymentID(1L);
        completedDto.setStatus(PaymentStatus.SUCCESS);

        Payment pendingDto = new Payment();
        pendingDto.setPaymentID(2L);
        pendingDto.setStatus(PaymentStatus.SUCCESS);

        Payment refundedDto = new Payment();
        refundedDto.setPaymentID(3L);
        refundedDto.setStatus(PaymentStatus.SUCCESS);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(completedEntity, Payment.class)).thenReturn(completedDto);
        when(mapper.map(pendingEntity, Payment.class)).thenReturn(pendingDto);
        when(mapper.map(refundedEntity, Payment.class)).thenReturn(refundedDto);

        // Act
        List<Payment> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(PaymentStatus.SUCCESS, result.get(0).getStatus());
        assertEquals(PaymentStatus.SUCCESS, result.get(1).getStatus());
        assertEquals(PaymentStatus.SUCCESS, result.get(2).getStatus());
    }
}