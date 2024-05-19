package com.studi.jo.common.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void testValidNormalPrice() {
        Price price = new Price(new BigDecimal("19.99"));
        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertTrue(violations.isEmpty());
        assertEquals(new BigDecimal("19.99"), price.getValue());
    }

    @Test
    public void testValidPriceRounding() {
        Price price = new Price(new BigDecimal("9.999"));
        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertTrue(violations.isEmpty());
        assertEquals(new BigDecimal("9.99"), price.getValue());

        price = new Price(new BigDecimal("9.9"));
        violations = validator.validate(price);
        assertTrue(violations.isEmpty());
        assertEquals(new BigDecimal("9.90"), price.getValue());
    }

    @Test
    public void testInvalidPriceWithLargeIntegerPart() {
        Price price = new Price(new BigDecimal("1234567890.12"));
        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertFalse(violations.isEmpty());
        assertEquals("Price must be inferior to 500", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidNegativePrice() {
        Price price = new Price(new BigDecimal("-1.00"));
        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertFalse(violations.isEmpty());
        assertEquals("Price must be greater or equal to zero", violations.iterator().next().getMessage());
    }
}
