package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.repository.AdminRepository;
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
class AdminServiceImplTest {

    @Mock
    private AdminRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AdminServiceImpl service;

    private Admin sampleDto;
    private AdminEntity sampleEntity;

    @BeforeEach
    void setUp() {
        sampleDto = new Admin(1L, "Alice", "123456789V", "alice@example.com", "secret");
        sampleEntity = AdminEntity.builder()
                .adminID(1L)
                .name("Alice")
                .nic("123456789V")
                .email("alice@example.com")
                .password("secret")
                .build();
    }

    @Test
    void add_shouldCallSaveWithMappedEntity_andReturnNull() {
        when(mapper.map(sampleDto, AdminEntity.class)).thenReturn(sampleEntity);

        AdminEntity result = service.add(sampleDto);

        // method currently returns null but should call repository.save
        verify(repository, times(1)).save(sampleEntity);
        assertNull(result, "Current implementation returns null");
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        service.delete(5L);
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void update_whenEntityExists_shouldSaveUpdatedEntityWithPreservedId() {
        Admin updatedDto = new Admin(1L, "Alice Updated", "987654321V", "alice2@example.com", "newpass");
        AdminEntity existing = AdminEntity.builder()
                .adminID(1L)
                .name("Alice")
                .nic("123456789V")
                .email("alice@example.com")
                .password("secret")
                .build();

        AdminEntity mappedUpdated = AdminEntity.builder()
                // intentionally not setting adminID here to simulate mapping behavior
                .name("Alice Updated")
                .nic("987654321V")
                .email("alice2@example.com")
                .password("newpass")
                .build();

        when(repository.findById(updatedDto.getAdminID())).thenReturn(Optional.of(existing));
        when(mapper.map(updatedDto, AdminEntity.class)).thenReturn(mappedUpdated);

        service.update(updatedDto);

        ArgumentCaptor<AdminEntity> captor = ArgumentCaptor.forClass(AdminEntity.class);
        verify(repository, times(1)).save(captor.capture());

        AdminEntity saved = captor.getValue();
        assertEquals(existing.getAdminID(), saved.getAdminID(), "Admin ID should be preserved from existing entity");
        assertEquals(mappedUpdated.getName(), saved.getName());
        assertEquals(mappedUpdated.getNic(), saved.getNic());
        assertEquals(mappedUpdated.getEmail(), saved.getEmail());
        assertEquals(mappedUpdated.getPassword(), saved.getPassword());
    }

    @Test
    void update_whenEntityNotFound_shouldThrowEntityNotFoundException() {
        Admin updatedDto = new Admin(99L, "NoOne", "000", "noone@example.com", "nopass");
        when(repository.findById(updatedDto.getAdminID())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(updatedDto));
        verify(repository, never()).save(any());
    }

    @Test
    void searchById_whenFound_shouldReturnMappedAdminDto() {
        Optional<AdminEntity> opt = Optional.of(sampleEntity);
        Admin mappedDto = sampleDto;

        when(repository.findById(1L)).thenReturn(opt);
        // the implementation currently calls mapper.map(Optional<AdminEntity>, Admin.class)
        when(mapper.map(opt, Admin.class)).thenReturn(mappedDto);

        Admin result = service.searchById(1L);

        assertNotNull(result);
        assertEquals(mappedDto, result);
        verify(mapper, times(1)).map(opt, Admin.class);
    }

    @Test
    void getAll_shouldReturnMappedList() {
        AdminEntity e1 = AdminEntity.builder().adminID(1L).name("A").nic("n1").email("a@a").password("p").build();
        AdminEntity e2 = AdminEntity.builder().adminID(2L).name("B").nic("n2").email("b@b").password("p").build();
        List<AdminEntity> entities = List.of(e1, e2);

        Admin d1 = new Admin(1L, "A", "n1", "a@a", "p");
        Admin d2 = new Admin(2L, "B", "n2", "b@b", "p");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(e1, Admin.class)).thenReturn(d1);
        when(mapper.map(e2, Admin.class)).thenReturn(d2);

        List<Admin> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(d1));
        assertTrue(result.contains(d2));
        verify(mapper, times(1)).map(e1, Admin.class);
        verify(mapper, times(1)).map(e2, Admin.class);
    }

    @Test
    void getAll_whenNoEntities_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Admin> result = service.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}