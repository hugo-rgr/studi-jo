package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class UserDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUserDTO() {
        FirstName firstName = new FirstName("John");
        LastName lastName = new LastName("Smith");
        Email email = new Email("john.smith@example.com");
        String password = "APassword6!";

        UserDTO userDTO = new UserDTO(firstName, lastName, email, password);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertEquals(0, violations.size());
    }

    @Test
    public void testValidToUser() {
        FirstName firstName = new FirstName("John");
        LastName lastName = new LastName("Smith");
        Email email = new Email("john.smith@example.com");
        String password = "APassword6!";

        UserDTO userDTO = new UserDTO(firstName, lastName, email, password);
        Role role = Role.CLIENT;
        User user = userDTO.toUser(role);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertEquals(role, user.getRole());
    }

    @Test
    public void testInvalidUserDTO() {
        FirstName firstName = new FirstName("");
        LastName lastName = new LastName("");
        Email email = new Email("invalid-email");
        String password = "invalid";

        UserDTO userDTO = new UserDTO(firstName, lastName, email, password);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());
    }
}
