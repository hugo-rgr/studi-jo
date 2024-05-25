package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class EmailTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidEmail() {
        Email email = new Email("test@example.com");
        Set<ConstraintViolation<Email>> violations = validator.validate(email);
        assertTrue(violations.isEmpty());
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    public void testInvalidEmail() {
        // Invalid email format
        Email email = new Email("invalid-email");
        Set<ConstraintViolation<Email>> violations = validator.validate(email);
        assertFalse(violations.isEmpty());
        assertEquals("Email must be valid", violations.iterator().next().getMessage());

        // Email is too long
        StringBuilder longEmail = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longEmail.append("a");
        }
        longEmail.append("@example.com");
        email = new Email(longEmail.toString());
        violations = validator.validate(email);
        assertFalse(violations.isEmpty());
        assertEquals("Email length cannot be greater than 50 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testSanitizedEmail() {
        Email email = new Email("<script>alert('XSS')</script>@example.com");
        assertEquals("&lt;script&gt;alert(&#39;XSS&#39;)&lt;/script&gt;@example.com", email.getValue());
    }
}

