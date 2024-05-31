package com.studi.jo.ticket.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.user.domain.UserKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class TicketTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTicket() {
        UserKey userKey = new UserKey();
        PurchaseKey purchaseKey = new PurchaseKey();
        Ticket ticket = new Ticket(null, "Offre familiale", userKey, purchaseKey, TicketValidityStatus.VALID);

        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidTicketNullOfferName() {
        UserKey userKey = new UserKey();
        PurchaseKey purchaseKey = new PurchaseKey();
        Ticket ticket = new Ticket(null, null, userKey, purchaseKey, TicketValidityStatus.VALID);

        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

        assertEquals(1, violations.size());
        ConstraintViolation<Ticket> violation = violations.iterator().next();
        assertEquals("offer name must not be null", violation.getMessage());
        assertEquals("offerName", violation.getPropertyPath().toString());
    }

    @Test
    public void testInvalidTicketNullParameters() {
        UserKey userKey = new UserKey();
        PurchaseKey purchaseKey = new PurchaseKey();
        Ticket ticket = new Ticket(null, "Offer Name", userKey, purchaseKey, null);

        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

        assertEquals(1, violations.size());
        ConstraintViolation<Ticket> violation = violations.iterator().next();
        assertEquals("ticket validity status must not be null", violation.getMessage());
        assertEquals("validityStatus", violation.getPropertyPath().toString());
    }
}
