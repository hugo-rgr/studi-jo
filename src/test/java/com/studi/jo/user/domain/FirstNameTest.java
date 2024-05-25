package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class FirstNameTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidFirstName() {
        FirstName firstName = new FirstName("John");
        Set<ConstraintViolation<FirstName>> violations = validator.validate(firstName);
        assertTrue(violations.isEmpty());
        assertEquals("John", firstName.getValue());
    }

    @Test
    public void testSanitizedFirstName() {
        FirstName firstName = new FirstName("  John   ");
        assertEquals("John", firstName.getValue());

        firstName = new FirstName("John\tDoe");
        assertEquals("John Doe", firstName.getValue());

        firstName = new FirstName("<script>alert('XSS')</script>");
        assertEquals("&lt;script&gt;alert('XSS')&lt;/script&gt;", firstName.getValue());
    }

    @Test
    public void testInvalidFirstNameTooLong() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longName.append("a");
        }
        FirstName firstName = new FirstName(longName.toString());
        Set<ConstraintViolation<FirstName>> violations = validator.validate(firstName);
        assertFalse(violations.isEmpty());
        assertEquals("First name length cannot be greater than 50 characters", violations.iterator().next().getMessage());
    }
}

