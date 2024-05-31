package com.studi.jo.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.studi.jo.user.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceIntegrationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO(new FirstName("John"), new LastName("Doe"), new Email("john.doe@example.com"), "ValidPass1!");
        Role role = Role.CLIENT;
        User user = userDTO.toUser(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userDTO, role);

        assertEquals("John", createdUser.getFirstName().getValue());
        assertEquals("Doe", createdUser.getLastName().getValue());
        assertEquals("john.doe@example.com", createdUser.getEmail().getValue());
    }

    @Test
    public void testGetUserById_UserExists() {
        User user = new User(1L, new FirstName("John"), new LastName("Doe"), new Email("john.doe@example.com"), new Password("ValidPass1!"), new UserKey(), Role.CLIENT);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);
        assertEquals(1L, foundUser.getId());
        assertEquals("John", foundUser.getFirstName().getValue());
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("ERROR: User with id 1 not found.", thrown.getMessage());
    }

    @Test
    public void testGetUserByEmail_UserExists() {
        User user = new User(1L, new FirstName("John"), new LastName("Doe"), new Email("john.doe@example.com"), new Password("ValidPass1!"), new UserKey(), Role.CLIENT);
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", foundUser.getEmail().getValue());
    }

    @Test
    public void testGetUserByEmail_UserDoesNotExist() {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserByEmail("john.doe@example.com");
        });

        assertEquals("User not found with email: john.doe@example.com", thrown.getMessage());
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        User user = new User(1L, new FirstName("John"), new LastName("Doe"), new Email("john.doe@example.com"), new Password("ValidPass1!"), new UserKey(), Role.CLIENT);
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("john.doe@example.com");
        assertEquals("john.doe@example.com", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserDoesNotExist() {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("john.doe@example.com");
        });

        assertEquals("User not found with email: john.doe@example.com", thrown.getMessage());
    }
}
