package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Vehicle;
import edu.icet.solorent.entity.VehicleEntity;
import edu.icet.solorent.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private VehicleServiceImpl service;

    private Vehicle sampleVehicleDto;
    private VehicleEntity sampleVehicleEntity;

    @BeforeEach
    void setUp() {
        sampleVehicleDto = new Vehicle();
        sampleVehicleDto.setVehicleID(1L);
        sampleVehicleDto.setBrand("Toyota");
        sampleVehicleDto.setFuelType("Petrol");
        sampleVehicleDto.setRegisterNumber("ABC-1234");
        sampleVehicleDto.setPricePerDay(5000.0);
        sampleVehicleDto.setImageURl("https://example.com/toyota.jpg");
        sampleVehicleDto.setDescription("Reliable sedan with excellent fuel economy");

        sampleVehicleEntity = new VehicleEntity();
        sampleVehicleEntity.setVehicleID(1L);
        sampleVehicleEntity.setBrand("Toyota");
        sampleVehicleEntity.setFuelType("Petrol");
        sampleVehicleEntity.setRegisterNumber("ABC-1234");
        sampleVehicleEntity.setPricePerDay(5000.0);
        sampleVehicleEntity.setImageURl("https://example.com/toyota.jpg");
        sampleVehicleEntity.setDescription("Reliable sedan with excellent fuel economy");
    }

    @Test
    void add_shouldMapSaveAndReturnVehicle() {
        // Arrange
        VehicleEntity savedEntity = new VehicleEntity();
        savedEntity.setVehicleID(1L);
        savedEntity.setBrand("Toyota");
        savedEntity.setFuelType("Petrol");
        savedEntity.setRegisterNumber("ABC-1234");
        savedEntity.setPricePerDay(5000.0);

        Vehicle savedDto = new Vehicle();
        savedDto.setVehicleID(1L);
        savedDto.setBrand("Toyota");
        savedDto.setFuelType("Petrol");
        savedDto.setRegisterNumber("ABC-1234");
        savedDto.setPricePerDay(5000.0);

        when(mapper.map(sampleVehicleDto, VehicleEntity.class)).thenReturn(sampleVehicleEntity);
        when(repository.save(sampleVehicleEntity)).thenReturn(savedEntity);
        when(mapper.map(savedEntity, Vehicle.class)).thenReturn(savedDto);

        // Act
        Optional<Vehicle> result = service.add(sampleVehicleDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getVehicleID());
        assertEquals("Toyota", result.get().getBrand());
        assertEquals("ABC-1234", result.get().getRegisterNumber());
        verify(mapper, times(1)).map(sampleVehicleDto, VehicleEntity.class);
        verify(repository, times(1)).save(sampleVehicleEntity);
        verify(mapper, times(1)).map(savedEntity, Vehicle.class);
    }

    @Test
    void add_shouldReturnVehicleWithAllFields() {
        // Arrange
        Vehicle inputDto = new Vehicle();
        inputDto.setBrand("Honda");
        inputDto.setFuelType("Diesel");
        inputDto.setRegisterNumber("XYZ-5678");
        inputDto.setPricePerDay(6000.0);
        inputDto.setImageURl("https://example.com/honda.jpg");
        inputDto.setDescription("Comfortable SUV for family trips");

        VehicleEntity mappedEntity = new VehicleEntity();
        mappedEntity.setBrand("Honda");
        mappedEntity.setFuelType("Diesel");
        mappedEntity.setRegisterNumber("XYZ-5678");
        mappedEntity.setPricePerDay(6000.0);
        mappedEntity.setImageURl("https://example.com/honda.jpg");
        mappedEntity.setDescription("Comfortable SUV for family trips");

        VehicleEntity savedEntity = new VehicleEntity();
        savedEntity.setVehicleID(2L);
        savedEntity.setBrand("Honda");
        savedEntity.setFuelType("Diesel");
        savedEntity.setRegisterNumber("XYZ-5678");
        savedEntity.setPricePerDay(6000.0);
        savedEntity.setImageURl("https://example.com/honda.jpg");
        savedEntity.setDescription("Comfortable SUV for family trips");

        Vehicle savedDto = new Vehicle();
        savedDto.setVehicleID(2L);
        savedDto.setBrand("Honda");
        savedDto.setFuelType("Diesel");
        savedDto.setRegisterNumber("XYZ-5678");
        savedDto.setPricePerDay(6000.0);
        savedDto.setImageURl("https://example.com/honda.jpg");
        savedDto.setDescription("Comfortable SUV for family trips");

        when(mapper.map(inputDto, VehicleEntity.class)).thenReturn(mappedEntity);
        when(repository.save(mappedEntity)).thenReturn(savedEntity);
        when(mapper.map(savedEntity, Vehicle.class)).thenReturn(savedDto);

        // Act
        Optional<Vehicle> result = service.add(inputDto);

        // Assert
        assertTrue(result.isPresent());
        Vehicle saved = result.get();
        assertEquals(2L, saved.getVehicleID());
        assertEquals("Honda", saved.getBrand());
        assertEquals("Diesel", saved.getFuelType());
        assertEquals("XYZ-5678", saved.getRegisterNumber());
        assertEquals(6000.0, saved.getPricePerDay());
        assertEquals("https://example.com/honda.jpg", saved.getImageURl());
        assertEquals("Comfortable SUV for family trips", saved.getDescription());
    }

    @Test
    void add_shouldGenerateIdForNewVehicle() {
        // Arrange
        Vehicle newVehicle = new Vehicle();
        newVehicle.setBrand("Nissan");
        newVehicle.setRegisterNumber("DEF-9999");

        VehicleEntity entityWithoutId = new VehicleEntity();
        entityWithoutId.setBrand("Nissan");
        entityWithoutId.setRegisterNumber("DEF-9999");

        VehicleEntity savedEntityWithId = new VehicleEntity();
        savedEntityWithId.setVehicleID(10L);
        savedEntityWithId.setBrand("Nissan");
        savedEntityWithId.setRegisterNumber("DEF-9999");

        Vehicle dtoWithId = new Vehicle();
        dtoWithId.setVehicleID(10L);
        dtoWithId.setBrand("Nissan");
        dtoWithId.setRegisterNumber("DEF-9999");

        when(mapper.map(newVehicle, VehicleEntity.class)).thenReturn(entityWithoutId);
        when(repository.save(entityWithoutId)).thenReturn(savedEntityWithId);
        when(mapper.map(savedEntityWithId, Vehicle.class)).thenReturn(dtoWithId);

        // Act
        Optional<Vehicle> result = service.add(newVehicle);

        // Assert
        assertTrue(result.isPresent());
        assertNotNull(result.get().getVehicleID());
        assertEquals(10L, result.get().getVehicleID());
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
    void update_whenVehicleExists_shouldUpdateWithPreservedId() {
        // Arrange
        Vehicle updateDto = new Vehicle();
        updateDto.setVehicleID(1L);
        updateDto.setBrand("Toyota Updated");
        updateDto.setFuelType("Hybrid");
        updateDto.setRegisterNumber("ABC-1234");
        updateDto.setPricePerDay(5500.0);
        updateDto.setImageURl("https://example.com/toyota-new.jpg");
        updateDto.setDescription("Updated description");

        VehicleEntity existingEntity = new VehicleEntity();
        existingEntity.setVehicleID(1L);
        existingEntity.setBrand("Toyota");
        existingEntity.setFuelType("Petrol");

        VehicleEntity mappedEntity = new VehicleEntity();
        mappedEntity.setBrand("Toyota Updated");
        mappedEntity.setFuelType("Hybrid");
        mappedEntity.setRegisterNumber("ABC-1234");
        mappedEntity.setPricePerDay(5500.0);
        mappedEntity.setImageURl("https://example.com/toyota-new.jpg");
        mappedEntity.setDescription("Updated description");

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(mapper.map(updateDto, VehicleEntity.class)).thenReturn(mappedEntity);

        // Act
        service.update(updateDto);

        // Assert
        ArgumentCaptor<VehicleEntity> captor = ArgumentCaptor.forClass(VehicleEntity.class);
        verify(repository, times(1)).save(captor.capture());

        VehicleEntity saved = captor.getValue();
        assertEquals(1L, saved.getVehicleID());
        assertEquals("Toyota Updated", saved.getBrand());
        assertEquals("Hybrid", saved.getFuelType());
        assertEquals(5500.0, saved.getPricePerDay());
    }

    @Test
    void update_whenVehicleNotFound_shouldThrowEntityNotFoundException() {
        // Arrange
        Vehicle updateDto = new Vehicle();
        updateDto.setVehicleID(999L);
        updateDto.setBrand("NonExistent");

        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> service.update(updateDto)
        );

        assertEquals("Admin not found", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void update_shouldMapAndSaveVehicle() {
        // Arrange
        when(repository.findById(sampleVehicleDto.getVehicleID())).thenReturn(Optional.of(sampleVehicleEntity));
        when(mapper.map(sampleVehicleDto, VehicleEntity.class)).thenReturn(sampleVehicleEntity);

        // Act
        service.update(sampleVehicleDto);

        // Assert
        verify(mapper, times(1)).map(sampleVehicleDto, VehicleEntity.class);
        verify(repository, times(1)).save(any(VehicleEntity.class));
    }

    @Test
    void searchByName_shouldReturnListOfVehiclesWithMatchingBrand() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity();
        entity1.setVehicleID(1L);
        entity1.setBrand("Toyota");
        entity1.setFuelType("Petrol");
        entity1.setRegisterNumber("ABC-1111");

        VehicleEntity entity2 = new VehicleEntity();
        entity2.setVehicleID(2L);
        entity2.setBrand("Toyota");
        entity2.setFuelType("Diesel");
        entity2.setRegisterNumber("ABC-2222");

        List<VehicleEntity> entities = List.of(entity1, entity2);

        Vehicle dto1 = new Vehicle();
        dto1.setVehicleID(1L);
        dto1.setBrand("Toyota");
        dto1.setFuelType("Petrol");
        dto1.setRegisterNumber("ABC-1111");

        Vehicle dto2 = new Vehicle();
        dto2.setVehicleID(2L);
        dto2.setBrand("Toyota");
        dto2.setFuelType("Diesel");
        dto2.setRegisterNumber("ABC-2222");

        when(repository.findByBrand("Toyota")).thenReturn((VehicleEntity) entities);
        when(mapper.map(entity1, Vehicle.class)).thenReturn(dto1);
        when(mapper.map(entity2, Vehicle.class)).thenReturn(dto2);

        // Act
        List<Vehicle> result = service.searchByName("Toyota");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals("Toyota", result.get(0).getBrand());
        assertEquals("Toyota", result.get(1).getBrand());
        verify(repository, times(1)).findByBrand("Toyota");
    }

    @Test
    void searchByName_whenNoBrandMatch_shouldReturnEmptyList() {
        // Arrange
        when(repository.findByBrand("NonExistentBrand")).thenReturn(new VehicleEntity());

        // Act
        List<Vehicle> result = service.searchByName("NonExistentBrand");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findByBrand("NonExistentBrand");
    }

    @Test
    void searchByName_withDifferentBrands_shouldReturnCorrectVehicles() {
        // Arrange
        VehicleEntity hondaEntity = new VehicleEntity();
        hondaEntity.setVehicleID(5L);
        hondaEntity.setBrand("Honda");
        hondaEntity.setRegisterNumber("HON-123");

        Vehicle hondaDto = new Vehicle();
        hondaDto.setVehicleID(5L);
        hondaDto.setBrand("Honda");
        hondaDto.setRegisterNumber("HON-123");

        when(repository.findByBrand("Honda")).thenReturn((VehicleEntity) List.of(hondaEntity));
        when(mapper.map(hondaEntity, Vehicle.class)).thenReturn(hondaDto);

        // Act
        List<Vehicle> result = service.searchByName("Honda");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getBrand());
        assertEquals("HON-123", result.get(0).getRegisterNumber());
    }

    @Test
    void getAll_shouldReturnListOfAllVehicles() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity();
        entity1.setVehicleID(1L);
        entity1.setBrand("Toyota");
        entity1.setFuelType("Petrol");
        entity1.setPricePerDay(5000.0);

        VehicleEntity entity2 = new VehicleEntity();
        entity2.setVehicleID(2L);
        entity2.setBrand("Honda");
        entity2.setFuelType("Diesel");
        entity2.setPricePerDay(6000.0);

        List<VehicleEntity> entities = List.of(entity1, entity2);

        Vehicle dto1 = new Vehicle();
        dto1.setVehicleID(1L);
        dto1.setBrand("Toyota");
        dto1.setFuelType("Petrol");
        dto1.setPricePerDay(5000.0);

        Vehicle dto2 = new Vehicle();
        dto2.setVehicleID(2L);
        dto2.setBrand("Honda");
        dto2.setFuelType("Diesel");
        dto2.setPricePerDay(6000.0);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Vehicle.class)).thenReturn(dto1);
        when(mapper.map(entity2, Vehicle.class)).thenReturn(dto2);

        // Act
        List<Vehicle> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals("Toyota", result.get(0).getBrand());
        assertEquals("Honda", result.get(1).getBrand());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(entity1, Vehicle.class);
        verify(mapper, times(1)).map(entity2, Vehicle.class);
    }

    @Test
    void getAll_whenNoVehicles_shouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Vehicle> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).map(any(VehicleEntity.class), eq(Vehicle.class));
    }

    @Test
    void getAll_withMultipleVehicles_shouldMapAllCorrectly() {
        // Arrange
        List<VehicleEntity> entities = new ArrayList<>();
        List<Vehicle> expectedDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            VehicleEntity entity = new VehicleEntity();
            entity.setVehicleID((long) i);
            entity.setBrand("Brand " + i);
            entity.setFuelType(i % 2 == 0 ? "Diesel" : "Petrol");
            entity.setRegisterNumber("REG-" + i);
            entity.setPricePerDay(5000.0 * i);
            entities.add(entity);

            Vehicle dto = new Vehicle();
            dto.setVehicleID((long) i);
            dto.setBrand("Brand " + i);
            dto.setFuelType(i % 2 == 0 ? "Diesel" : "Petrol");
            dto.setRegisterNumber("REG-" + i);
            dto.setPricePerDay(5000.0 * i);
            expectedDtos.add(dto);

            when(mapper.map(entity, Vehicle.class)).thenReturn(dto);
        }

        when(repository.findAll()).thenReturn(entities);

        // Act
        List<Vehicle> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDtos.get(i).getVehicleID(), result.get(i).getVehicleID());
            assertEquals(expectedDtos.get(i).getBrand(), result.get(i).getBrand());
            assertEquals(expectedDtos.get(i).getFuelType(), result.get(i).getFuelType());
            assertEquals(expectedDtos.get(i).getPricePerDay(), result.get(i).getPricePerDay());
        }
        verify(repository, times(1)).findAll();
        verify(mapper, times(5)).map(any(VehicleEntity.class), eq(Vehicle.class));
    }

    @Test
    void getAll_shouldPreserveOrderFromRepository() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity();
        entity1.setVehicleID(3L);
        entity1.setBrand("Third");

        VehicleEntity entity2 = new VehicleEntity();
        entity2.setVehicleID(1L);
        entity2.setBrand("First");

        VehicleEntity entity3 = new VehicleEntity();
        entity3.setVehicleID(2L);
        entity3.setBrand("Second");

        List<VehicleEntity> entities = List.of(entity1, entity2, entity3);

        Vehicle dto1 = new Vehicle();
        dto1.setVehicleID(3L);
        dto1.setBrand("Third");

        Vehicle dto2 = new Vehicle();
        dto2.setVehicleID(1L);
        dto2.setBrand("First");

        Vehicle dto3 = new Vehicle();
        dto3.setVehicleID(2L);
        dto3.setBrand("Second");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Vehicle.class)).thenReturn(dto1);
        when(mapper.map(entity2, Vehicle.class)).thenReturn(dto2);
        when(mapper.map(entity3, Vehicle.class)).thenReturn(dto3);

        // Act
        List<Vehicle> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(3L, result.get(0).getVehicleID());
        assertEquals(1L, result.get(1).getVehicleID());
        assertEquals(2L, result.get(2).getVehicleID());
        assertEquals("Third", result.get(0).getBrand());
        assertEquals("First", result.get(1).getBrand());
        assertEquals("Second", result.get(2).getBrand());
    }
}

