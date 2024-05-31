package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    private Validator validator;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testValidPasswordEncoding() {
        String rawPassword = "PassToEncode1!";
        Password password = new Password(rawPassword);
        assertNotEquals(rawPassword, password.getValue());

        assertTrue(passwordEncoder.matches(rawPassword, password.getValue()));
    }
}
