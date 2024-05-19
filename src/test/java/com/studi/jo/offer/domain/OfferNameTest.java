package com.studi.jo.offer.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class OfferNameTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void testValidNormalOfferName() {
        OfferName offerName = new OfferName("Offer Name");
        Set<ConstraintViolation<OfferName>> violations = validator.validate(offerName);
        assertTrue(violations.isEmpty());
        assertEquals("Offer Name", offerName.getValue());
    }

    @Test
    public void testValidSanitizeOfferName() {
        OfferName offerName = new OfferName("  Offer   Name  ");
        assertEquals("Offer Name", offerName.getValue());

        offerName = new OfferName("Offer\n\r\tName");
        assertEquals("Offer Name", offerName.getValue());

        offerName = new OfferName("<script>alert('XSS')</script>");
        assertEquals("&lt;script&gt;alert('XSS')&lt;/script&gt;", offerName.getValue());
    }

    @Test
    public void testInvalidEmptyOfferName() {
        OfferName offerName = new OfferName("");
        Set<ConstraintViolation<OfferName>> violations = validator.validate(offerName);
        assertFalse(violations.isEmpty());
        assertEquals("Offer name cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidOfferNameTooLong() {
        OfferName offerName = new OfferName("Offernamethatiswaytoolong");
        Set<ConstraintViolation<OfferName>> violations = validator.validate(offerName);
        assertFalse(violations.isEmpty());
        assertEquals("Offer name length cannot be greater than 20 characters", violations.iterator().next().getMessage());
    }
}

