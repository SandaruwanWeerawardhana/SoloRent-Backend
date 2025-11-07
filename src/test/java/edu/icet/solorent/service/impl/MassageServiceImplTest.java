package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Massage;
import edu.icet.solorent.entity.MassageEntity;
import edu.icet.solorent.repository.MassageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MassageServiceImplTest {

    @Mock
    private MassageRepository repository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MassageServiceImpl service;

    private Massage sampleMassageDto;
    private MassageEntity sampleMassageEntity;

    @BeforeEach
    void setUp() {
        sampleMassageDto = new Massage();
        sampleMassageDto.setMassageID(1L);
        sampleMassageDto.setName("John Doe");
        sampleMassageDto.setEmail("john@example.com");
        sampleMassageDto.setContact("0771234567");
        sampleMassageDto.setMassage("I would like to inquire about vehicle rental options.");

        sampleMassageEntity = new MassageEntity();
        sampleMassageEntity.setMassageID(1L);
        sampleMassageEntity.setName("John Doe");
        sampleMassageEntity.setEmail("john@example.com");
        sampleMassageEntity.setContact("0771234567");
        sampleMassageEntity.setMassage("I would like to inquire about vehicle rental options.");
    }

    @Test
    void add_shouldMapSaveAndSendEmail() {
        // Arrange
        when(mapper.map(sampleMassageDto, MassageEntity.class)).thenReturn(sampleMassageEntity);
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        service.add(sampleMassageDto);

        // Assert
        verify(mapper, times(1)).map(sampleMassageDto, MassageEntity.class);
        verify(repository, times(1)).save(sampleMassageEntity);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void add_shouldSendEmailWithCorrectContent() {
        // Arrange
        when(mapper.map(sampleMassageDto, MassageEntity.class)).thenReturn(sampleMassageEntity);
        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        service.add(sampleMassageDto);

        // Assert
        verify(mailSender, times(1)).send(mailCaptor.capture());
        SimpleMailMessage sentMail = mailCaptor.getValue();

        assertNotNull(sentMail);
        assertEquals("bitzlk01@gmail.com", sentMail.getTo()[0]);
        assertEquals("New Contact Message from John Doe", sentMail.getSubject());
        assertTrue(sentMail.getText().contains("Name: John Doe"));
        assertTrue(sentMail.getText().contains("Email: john@example.com"));
        assertTrue(sentMail.getText().contains("Phone: 0771234567"));
        assertTrue(sentMail.getText().contains("I would like to inquire about vehicle rental options."));
    }

    @Test
    void add_whenEmailSendingFails_shouldStillSaveToDatabase() {
        // Arrange
        when(mapper.map(sampleMassageDto, MassageEntity.class)).thenReturn(sampleMassageEntity);
        doThrow(new RuntimeException("Email server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act - should not throw exception
        assertDoesNotThrow(() -> service.add(sampleMassageDto));

        // Assert
        verify(repository, times(1)).save(sampleMassageEntity);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void add_shouldMapAllFieldsCorrectly() {
        // Arrange
        Massage dto = new Massage();
        dto.setName("Jane Smith");
        dto.setEmail("jane@example.com");
        dto.setContact("0779876543");
        dto.setMassage("Need urgent rental information");

        MassageEntity mappedEntity = new MassageEntity();
        mappedEntity.setName("Jane Smith");
        mappedEntity.setEmail("jane@example.com");
        mappedEntity.setContact("0779876543");
        mappedEntity.setMassage("Need urgent rental information");

        when(mapper.map(dto, MassageEntity.class)).thenReturn(mappedEntity);

        // Act
        service.add(dto);

        // Assert
        ArgumentCaptor<MassageEntity> captor = ArgumentCaptor.forClass(MassageEntity.class);
        verify(repository, times(1)).save(captor.capture());

        MassageEntity saved = captor.getValue();
        assertEquals("Jane Smith", saved.getName());
        assertEquals("jane@example.com", saved.getEmail());
        assertEquals("0779876543", saved.getContact());
        assertEquals("Need urgent rental information", saved.getMassage());
    }

    @Test
    void add_shouldSetCorrectEmailRecipient() {
        // Arrange
        when(mapper.map(any(Massage.class), eq(MassageEntity.class))).thenReturn(sampleMassageEntity);
        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        service.add(sampleMassageDto);

        // Assert
        verify(mailSender).send(mailCaptor.capture());
        SimpleMailMessage sentMail = mailCaptor.getValue();
        assertArrayEquals(new String[]{"bitzlk01@gmail.com"}, sentMail.getTo());
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
    void getAll_shouldReturnListOfMappedMassages() {
        // Arrange
        MassageEntity entity1 = new MassageEntity();
        entity1.setMassageID(1L);
        entity1.setName("Customer 1");
        entity1.setEmail("customer1@example.com");
        entity1.setContact("0771111111");
        entity1.setMassage("Message 1");

        MassageEntity entity2 = new MassageEntity();
        entity2.setMassageID(2L);
        entity2.setName("Customer 2");
        entity2.setEmail("customer2@example.com");
        entity2.setContact("0772222222");
        entity2.setMassage("Message 2");

        List<MassageEntity> entities = List.of(entity1, entity2);

        Massage dto1 = new Massage();
        dto1.setMassageID(1L);
        dto1.setName("Customer 1");
        dto1.setEmail("customer1@example.com");
        dto1.setContact("0771111111");
        dto1.setMassage("Message 1");

        Massage dto2 = new Massage();
        dto2.setMassageID(2L);
        dto2.setName("Customer 2");
        dto2.setEmail("customer2@example.com");
        dto2.setContact("0772222222");
        dto2.setMassage("Message 2");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Massage.class)).thenReturn(dto1);
        when(mapper.map(entity2, Massage.class)).thenReturn(dto2);

        // Act
        List<Massage> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        assertEquals("Customer 1", result.get(0).getName());
        assertEquals("Customer 2", result.get(1).getName());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(entity1, Massage.class);
        verify(mapper, times(1)).map(entity2, Massage.class);
    }

    @Test
    void getAll_whenNoMassages_shouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Massage> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).map(any(MassageEntity.class), eq(Massage.class));
    }

    @Test
    void getAll_withMultipleMassages_shouldMapAllCorrectly() {
        // Arrange
        List<MassageEntity> entities = new ArrayList<>();
        List<Massage> expectedDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            MassageEntity entity = new MassageEntity();
            entity.setMassageID((long) i);
            entity.setName("Customer " + i);
            entity.setEmail("customer" + i + "@example.com");
            entity.setContact("077000000" + i);
            entity.setMassage("Message " + i);
            entities.add(entity);

            Massage dto = new Massage();
            dto.setMassageID((long) i);
            dto.setName("Customer " + i);
            dto.setEmail("customer" + i + "@example.com");
            dto.setContact("077000000" + i);
            dto.setMassage("Message " + i);
            expectedDtos.add(dto);

            when(mapper.map(entity, Massage.class)).thenReturn(dto);
        }

        when(repository.findAll()).thenReturn(entities);

        // Act
        List<Massage> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDtos.get(i).getMassageID(), result.get(i).getMassageID());
            assertEquals(expectedDtos.get(i).getName(), result.get(i).getName());
            assertEquals(expectedDtos.get(i).getEmail(), result.get(i).getEmail());
            assertEquals(expectedDtos.get(i).getMassage(), result.get(i).getMassage());
        }
        verify(repository, times(1)).findAll();
        verify(mapper, times(5)).map(any(MassageEntity.class), eq(Massage.class));
    }

    @Test
    void getAll_shouldPreserveOrderFromRepository() {
        // Arrange
        MassageEntity entity1 = new MassageEntity();
        entity1.setMassageID(3L);
        entity1.setName("Third");

        MassageEntity entity2 = new MassageEntity();
        entity2.setMassageID(1L);
        entity2.setName("First");

        MassageEntity entity3 = new MassageEntity();
        entity3.setMassageID(2L);
        entity3.setName("Second");

        List<MassageEntity> entities = List.of(entity1, entity2, entity3);

        Massage dto1 = new Massage();
        dto1.setMassageID(3L);
        dto1.setName("Third");

        Massage dto2 = new Massage();
        dto2.setMassageID(1L);
        dto2.setName("First");

        Massage dto3 = new Massage();
        dto3.setMassageID(2L);
        dto3.setName("Second");

        when(repository.findAll()).thenReturn(entities);
        when(mapper.map(entity1, Massage.class)).thenReturn(dto1);
        when(mapper.map(entity2, Massage.class)).thenReturn(dto2);
        when(mapper.map(entity3, Massage.class)).thenReturn(dto3);

        // Act
        List<Massage> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(3L, result.get(0).getMassageID());
        assertEquals(1L, result.get(1).getMassageID());
        assertEquals(2L, result.get(2).getMassageID());
        assertEquals("Third", result.get(0).getName());
        assertEquals("First", result.get(1).getName());
        assertEquals("Second", result.get(2).getName());
    }
}