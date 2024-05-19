package com.studi.jo.offer.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.studi.jo.common.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class OfferTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidOffer() {
        OfferName name = new OfferName("Offer Name");
        OfferDescription description = new OfferDescription("Offer Description");
        Price price = new Price(new BigDecimal("24.99"));
        Offer offer = new Offer(null, name, description, price, 10);

        Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidOfferValueObjects() {
        OfferName name = new OfferName(""); // Invalid offer name
        OfferDescription description = new OfferDescription("OfferDescriptionthatiswaytoolongThereShouldBeLessCharactersHere"); // Invalid offer description
        Price price = new Price(new BigDecimal("-1.00")); // Invalid price
        Offer offer = new Offer(null, name, description, price, 10);

        Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
        assertEquals(3, violations.toArray().length);
    }

    @Test
    public void testInvalidOfferWithNegativeSalesNumber() {
        OfferName name = new OfferName("Offer Name");
        OfferDescription description = new OfferDescription("Offer Description");
        Price price = new Price(new BigDecimal("24.99"));
        Offer offer = new Offer(null, name, description, price, -5); // Invalid sales number

        Set<ConstraintViolation<Offer>> violations = validator.validate(offer);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Offer sales number must be greater or equal to zero")));
    }
}
