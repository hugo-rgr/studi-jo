package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class LastNameTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidLastName() {
        LastName lastName = new LastName("Doe");
        Set<ConstraintViolation<LastName>> violations = validator.validate(lastName);
        assertTrue(violations.isEmpty());
        assertEquals("Doe", lastName.getValue());
    }

    @Test
    public void testSanitizedLastName() {
        LastName lastName = new LastName("  Doe   ");
        assertEquals("Doe", lastName.getValue());

        lastName = new LastName("Doe\tSmith");
        assertEquals("Doe Smith", lastName.getValue());

        lastName = new LastName("<script>alert('XSS')</script>");
        assertEquals("&lt;script&gt;alert('XSS')&lt;/script&gt;", lastName.getValue());
    }

    @Test
    public void testInvalidLastNameTooLong() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longName.append("a");
        }
        LastName lastName = new LastName(longName.toString());
        Set<ConstraintViolation<LastName>> violations = validator.validate(lastName);
        assertFalse(violations.isEmpty());
        assertEquals("Last name length cannot be greater than 50 characters", violations.iterator().next().getMessage());
    }
}
