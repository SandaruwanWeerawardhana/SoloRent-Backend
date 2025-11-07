package edu.icet.solorent.service.impl;

import edu.icet.solorent.entity.User;
import edu.icet.solorent.repository.UserRepository;
import edu.icet.solorent.util.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailsServiceImp service;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1);
        sampleUser.setName("John Doe");
        sampleUser.setUsername("johndoe");
        sampleUser.setPassword("encodedPassword123");
        sampleUser.setRole(Role.ADMIN);
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUserDetails() {
        // Arrange
        String username = "johndoe";
        when(repository.findByUsername(username)).thenReturn(Optional.of(sampleUser));

        // Act
        UserDetails result = service.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("encodedPassword123", result.getPassword());
        verify(repository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnCorrectAuthorities() {
        // Arrange
        String username = "johndoe";
        when(repository.findByUsername(username)).thenReturn(Optional.of(sampleUser));

        // Act
        UserDetails result = service.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getAuthorities());
        assertFalse(result.getAuthorities().isEmpty());

        GrantedAuthority authority = result.getAuthorities().iterator().next();
        assertEquals("ADMIN", authority.getAuthority());
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUserWithAllFields() {
        // Arrange
        String username = "johndoe";
        when(repository.findByUsername(username)).thenReturn(Optional.of(sampleUser));

        // Act
        UserDetails result = service.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertInstanceOf(User.class, result);

        User user = (User) result;
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john doe", user.getUsername());
        assertEquals("encodedPassword123", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void loadUserByUsername_whenUserNotFound_shouldThrowUsernameNotFoundException() {
        // Arrange
        String username = "nonexistent";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername(username)
        );

        assertEquals("User not found", exception.getMessage());
        verify(repository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_withDifferentUsername_shouldCallRepositoryWithCorrectParameter() {
        // Arrange
        String username = "jane.smith";
        User anotherUser = new User();
        anotherUser.setId(2);
        anotherUser.setUsername("jane.smith");
        anotherUser.setPassword("password456");
        anotherUser.setRole(Role.ADMIN);

        when(repository.findByUsername(username)).thenReturn(Optional.of(anotherUser));

        // Act
        UserDetails result = service.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals("jane.smith", result.getUsername());
        verify(repository, times(1)).findByUsername("jane.smith");
        verify(repository, never()).findByUsername("johndoe");
    }

    @Test
    void loadUserByUsername_withNullUsername_shouldThrowUsernameNotFoundException() {
        // Arrange
        when(repository.findByUsername(null)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(null));
        verify(repository, times(1)).findByUsername(null);
    }

    @Test
    void loadUserByUsername_withEmptyUsername_shouldThrowUsernameNotFoundException() {
        // Arrange
        String emptyUsername = "";
        when(repository.findByUsername(emptyUsername)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(emptyUsername));
        verify(repository, times(1)).findByUsername(emptyUsername);
    }

    @Test
    void loadUserByUsername_whenCalledMultipleTimes_shouldQueryRepositoryEachTime() {
        // Arrange
        String username = "johndoe";
        when(repository.findByUsername(username)).thenReturn(Optional.of(sampleUser));

        // Act
        service.loadUserByUsername(username);
        service.loadUserByUsername(username);
        service.loadUserByUsername(username);

        // Assert
        verify(repository, times(3)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_withUserHavingDifferentRole_shouldReturnUserWithCorrectRole() {
        // Arrange
        User userUser = new User();
        userUser.setId(3);
        userUser.setUsername("regular");
        userUser.setPassword("password789");
        userUser.setRole(Role.ADMIN);

        when(repository.findByUsername("regular")).thenReturn(Optional.of(userUser));

        // Act
        UserDetails result = service.loadUserByUsername("regular");

        // Assert
        assertNotNull(result);
        assertInstanceOf(User.class, result);

        User user = (User) result;
        assertEquals(Role.ADMIN, user.getRole());

        GrantedAuthority authority = result.getAuthorities().iterator().next();
        assertEquals("USER", authority.getAuthority());
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetailsInterface() {
        // Arrange
        String username = "john doe";
        when(repository.findByUsername(username)).thenReturn(Optional.of(sampleUser));

        // Act
        UserDetails result = service.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertInstanceOf(UserDetails.class, result);
        assertNotNull(result.getAuthorities());
        assertNotNull(result.getUsername());
        result.getPassword();
    }
}