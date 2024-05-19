package com.studi.jo.offer.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class OfferDescriptionTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void testValidNormalOfferDescription() {
        OfferDescription offerDescription = new OfferDescription("Offer Description");
        Set<ConstraintViolation<OfferDescription>> violations = validator.validate(offerDescription);
        assertTrue(violations.isEmpty());
        assertEquals("Offer Description", offerDescription.getValue());
    }

    @Test
    public void testValidSanitizeOfferDescription() {
        OfferDescription offerDescription = new OfferDescription("  Offer   Description  ");
        assertEquals("Offer Description", offerDescription.getValue());

        offerDescription = new OfferDescription("Offer\n\r\tDescription");
        assertEquals("Offer Description", offerDescription.getValue());

        offerDescription = new OfferDescription("<script>alert('XSS')</script>");
        assertEquals("&lt;script&gt;alert('XSS')&lt;/script&gt;", offerDescription.getValue());
    }

    @Test
    public void testInvalidOfferDescriptionTooLong() {
        OfferDescription offerDescription = new OfferDescription("OfferDescriptionthatiswaytoolongThereShouldBeLessCharactersHere");
        Set<ConstraintViolation<OfferDescription>> violations = validator.validate(offerDescription);
        assertFalse(violations.isEmpty());
        assertEquals("Offer description length cannot be greater than 60 characters", violations.iterator().next().getMessage());
    }
}
