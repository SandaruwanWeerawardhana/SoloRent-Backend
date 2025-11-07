package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Maintenance;
import edu.icet.solorent.entity.MaintenanceEntity;
import edu.icet.solorent.repository.MaintetanceRepositry;
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
class MaintetanceServiceImplTest {

    @Mock
    private MaintetanceRepositry repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private MaintetanceServiceImpl service;

    private Maintenance sampleMaintenanceDto;
    private MaintenanceEntity sampleMaintenanceEntity;

    @BeforeEach
    void setUp() {
        Date testDate = new Date();

        sampleMaintenanceDto = new Maintenance();
        sampleMaintenanceDto.setMaintenanceID(1L);
        sampleMaintenanceDto.setVehicleID(100L);
        sampleMaintenanceDto.setDescription("Oil change and tire rotation");
        sampleMaintenanceDto.setCost(5000.0);
        sampleMaintenanceDto.setDate(testDate);

        sampleMaintenanceEntity = new MaintenanceEntity();
        sampleMaintenanceEntity.setMaintenanceID(1L);
        sampleMaintenanceEntity.setVehicleID(100L);
        sampleMaintenanceEntity.setDescription("Oil change and tire rotation");
        sampleMaintenanceEntity.setCost(5000.0);
        sampleMaintenanceEntity.setDate(testDate);
    }

    @Test
    void add_shouldMapAndSaveMaintenance() {
        // Arrange
        when(mapper.map(sampleMaintenanceDto, MaintenanceEntity.class)).thenReturn(sampleMaintenanceEntity);

        // Act
        service.add(sampleMaintenanceDto);

        // Assert
        verify(mapper, times(1)).map(sampleMaintenanceDto, MaintenanceEntity.class);
        verify(repository, times(1)).save(sampleMaintenanceEntity);
    }

    @Test
    void add_shouldCallRepositorySaveWithMappedEntity() {
        // Arrange
        MaintenanceEntity mappedEntity = new MaintenanceEntity();
        mappedEntity.setVehicleID(200L);
        mappedEntity.setDescription("Engine repair");
        mappedEntity.setCost(15000.0);
        mappedEntity.setDate(new Date());

        Maintenance dto = new Maintenance();
        dto.setVehicleID(200L);
        dto.setDescription("Engine repair");
        dto.setCost(15000.0);
        dto.setDate(new Date());

        when(mapper.map(dto, MaintenanceEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(dto);

        // Assert
        ArgumentCaptor<MaintenanceEntity> captor = ArgumentCaptor.forClass(MaintenanceEntity.class);
        verify(repository, times(1)).save(captor.capture());

        MaintenanceEntity saved = captor.getValue();
        assertEquals(200L, saved.getVehicleID());
        assertEquals("Engine repair", saved.getDescription());
        assertEquals(15000.0, saved.getCost());
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
    void update_shouldMapAndSaveMaintenance() {
        // Arrange
        Maintenance updatedDto = new Maintenance();
        updatedDto.setMaintenanceID(1L);
        updatedDto.setVehicleID(100L);
        updatedDto.setDescription("Updated description");
        updatedDto.setCost(7500.0);
        updatedDto.setDate(new Date());

        MaintenanceEntity mappedEntity = new MaintenanceEntity();
        mappedEntity.setMaintenanceID(1L);
        mappedEntity.setVehicleID(100L);
        mappedEntity.setDescription("Updated description");
        mappedEntity.setCost(7500.0);
        mappedEntity.setDate(new Date());

        when(mapper.map(updatedDto, MaintenanceEntity.class)).thenReturn(mappedEntity);

        // Act
        service.update(updatedDto);

        // Assert
        verify(mapper, times(1)).map(updatedDto, MaintenanceEntity.class);
        verify(repository, times(1)).save(mappedEntity);
    }

    @Test
    void update_shouldSaveWithUpdatedFields() {
        // Arrange
        when(mapper.map(sampleMaintenanceDto, MaintenanceEntity.class)).thenReturn(sampleMaintenanceEntity);

        // Act
        service.update(sampleMaintenanceDto);

        // Assert
        ArgumentCaptor<MaintenanceEntity> captor = ArgumentCaptor.forClass(MaintenanceEntity.class);
        verify(repository, times(1)).save(captor.capture());

        MaintenanceEntity saved = captor.getValue();
        assertEquals(sampleMaintenanceEntity.getMaintenanceID(), saved.getMaintenanceID());
        assertEquals(sampleMaintenanceEntity.getVehicleID(), saved.getVehicleID());
        assertEquals(sampleMaintenanceEntity.getDescription(), saved.getDescription());
        assertEquals(sampleMaintenanceEntity.getCost(), saved.getCost());
    }

    @Test
    void searchById_whenFound_shouldReturnOptionalWithEntity() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(sampleMaintenanceEntity));

        // Act
        Optional<MaintenanceEntity> result = service.searchById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleMaintenanceEntity, result.get());
        assertEquals(100L, result.get().getVehicleID());
        assertEquals("Oil change and tire rotation", result.get().getDescription());
        assertEquals(5000.0, result.get().getCost());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void searchById_whenNotFound_shouldReturnEmptyOptional() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<MaintenanceEntity> result = service.searchById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void searchById_withNullId_shouldCallRepository() {
        // Arrange
        when(repository.findById(null)).thenReturn(Optional.empty());

        // Act
        Optional<MaintenanceEntity> result = service.searchById(null);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(null);
    }

    @Test
    void getAll_shouldReturnListOfMappedMaintenance() {
        // Arrange
        MaintenanceEntity entity1 = new MaintenanceEntity();
        entity1.setMaintenanceID(1L);
        entity1.setVehicleID(100L);
        entity1.setDescription("Service 1");
        entity1.setCost(3000.0);
        entity1.setDate(new Date());

        MaintenanceEntity entity2 = new MaintenanceEntity();
        entity2.setMaintenanceID(2L);
        entity2.setVehicleID(200L);
        entity2.setDescription("Service 2");
        entity2.setCost(4500.0);
        entity2.setDate(new Date());

        List<MaintenanceEntity> entities = List.of(entity1, entity2);

        Maintenance dto1 = new Maintenance();
        dto1.setMaintenanceID(1L);
        dto1.setVehicleID(100L);
        dto1.setDescription("Service 1");
        dto1.setCost(3000.0);
        dto1.setDate(new Date());

        Maintenance dto2 = new Maintenance();
        dto2.setMaintenanceID(2L);
        dto2.setVehicleID(200L);
        dto2.setDescription("Service 2");
        dto2.setCost(4500.0);
        dto2.setDate(new Date());

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Maintenance.class)).thenReturn(dto1);
        when(mapper.map(entity2, Maintenance.class)).thenReturn(dto2);

        // Act
        List<Maintenance> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals("Service 1", result.get(0).getDescription());
        assertEquals("Service 2", result.get(1).getDescription());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(entity1, Maintenance.class);
        verify(mapper, times(1)).map(entity2, Maintenance.class);
    }

    @Test
    void getAll_whenNoMaintenance_shouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Maintenance> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).map(any(MaintenanceEntity.class), eq(Maintenance.class));
    }

    @Test
    void getAll_withMultipleMaintenance_shouldMapAllCorrectly() {
        // Arrange
        List<MaintenanceEntity> entities = new ArrayList<>();
        List<Maintenance> expectedDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            MaintenanceEntity entity = new MaintenanceEntity();
            entity.setMaintenanceID((long) i);
            entity.setVehicleID((long) (100 + i));
            entity.setDescription("Maintenance " + i);
            entity.setCost(1000.0 * i);
            entity.setDate(new Date());
            entities.add(entity);

            Maintenance dto = new Maintenance();
            dto.setMaintenanceID((long) i);
            dto.setVehicleID((long) (100 + i));
            dto.setDescription("Maintenance " + i);
            dto.setCost(1000.0 * i);
            dto.setDate(new Date());
            expectedDtos.add(dto);

            when(mapper.map(entity, Maintenance.class)).thenReturn(dto);
        }

        when(repository.findAll()).thenReturn(entities);

        // Act
        List<Maintenance> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDtos.get(i).getMaintenanceID(), result.get(i).getMaintenanceID());
            assertEquals(expectedDtos.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(expectedDtos.get(i).getCost(), result.get(i).getCost());
        }
        verify(repository, times(1)).findAll();
        verify(mapper, times(5)).map(any(MaintenanceEntity.class), eq(Maintenance.class));
    }

    @Test
    void getAll_shouldPreserveOrderFromRepository() {
        // Arrange
        MaintenanceEntity entity1 = new MaintenanceEntity();
        entity1.setMaintenanceID(3L);
        entity1.setVehicleID(300L);
        entity1.setDescription("Third");
        entity1.setCost(3000.0);

        MaintenanceEntity entity2 = new MaintenanceEntity();
        entity2.setMaintenanceID(1L);
        entity2.setVehicleID(100L);
        entity2.setDescription("First");
        entity2.setCost(1000.0);

        MaintenanceEntity entity3 = new MaintenanceEntity();
        entity3.setMaintenanceID(2L);
        entity3.setVehicleID(200L);
        entity3.setDescription("Second");
        entity3.setCost(2000.0);

        List<MaintenanceEntity> entities = List.of(entity1, entity2, entity3);

        Maintenance dto1 = new Maintenance();
        dto1.setMaintenanceID(3L);
        dto1.setDescription("Third");

        Maintenance dto2 = new Maintenance();
        dto2.setMaintenanceID(1L);
        dto2.setDescription("First");

        Maintenance dto3 = new Maintenance();
        dto3.setMaintenanceID(2L);
        dto3.setDescription("Second");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Maintenance.class)).thenReturn(dto1);
        when(mapper.map(entity2, Maintenance.class)).thenReturn(dto2);
        when(mapper.map(entity3, Maintenance.class)).thenReturn(dto3);

        // Act
        List<Maintenance> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(3L, result.get(0).getMaintenanceID());
        assertEquals(1L, result.get(1).getMaintenanceID());
        assertEquals(2L, result.get(2).getMaintenanceID());
        assertEquals("Third", result.get(0).getDescription());
        assertEquals("First", result.get(1).getDescription());
        assertEquals("Second", result.get(2).getDescription());
    }
}