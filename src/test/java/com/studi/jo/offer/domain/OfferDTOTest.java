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

public class OfferDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidOfferDTO() {
        OfferName name = new OfferName("Offer Name");
        OfferDescription description = new OfferDescription("Offer Description");
        Price price = new Price(new BigDecimal("24.99"));

        OfferDTO offerDTO = new OfferDTO(name, price, description);
        Set<ConstraintViolation<OfferDTO>> violations = validator.validate(offerDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidToOffer() {
        OfferName name = new OfferName("Offer Name");
        OfferDescription description = new OfferDescription("Offer Description");
        Price price = new Price(new BigDecimal("24.99"));

        OfferDTO offerDTO = new OfferDTO(name, price, description);
        Offer offer = offerDTO.toOffer();

        assertEquals(name, offer.getName());
        assertEquals(description, offer.getDescription());
        assertEquals(price, offer.getPrice());
    }

    @Test
    public void testInvalidOfferDTOValueObjects() {
        OfferName name = new OfferName(""); // Invalid name
        OfferDescription description = new OfferDescription("OfferDescriptionthatiswaytoolongThereShouldBeLessCharactersHere"); // Invalid description
        Price price = new Price(new BigDecimal("-1.00")); // Invalid price

        OfferDTO offerDTO = new OfferDTO(name, price, description);
        Set<ConstraintViolation<OfferDTO>> violations = validator.validate(offerDTO);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.toArray().length);
    }
}

