package com.studi.jo.ticket.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.user.domain.UserKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class TicketDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTicketDTO() {
        OfferName offerName = new OfferName("Offre duo");
        UserKey userKey = new UserKey();
        PurchaseKey purchaseKey = new PurchaseKey();
        TicketDTO ticketDTO = new TicketDTO(offerName, userKey, purchaseKey);

        Set<ConstraintViolation<TicketDTO>> violations = validator.validate(ticketDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidToTicket() {
        OfferName offerName = new OfferName("Concert");
        UserKey userKey = new UserKey();
        PurchaseKey purchaseKey = new PurchaseKey();
        TicketDTO ticketDTO = new TicketDTO(offerName, userKey, purchaseKey);

        Ticket ticket = ticketDTO.toTicket();

        assertEquals(offerName.getValue(), ticket.getOfferName());
        assertEquals(userKey, ticket.getUserKey());
        assertEquals(purchaseKey, ticket.getPurchaseKey());
        assertEquals(TicketValidityStatus.VALID, ticket.getValidityStatus());
    }

    @Test
    public void testInvalidTicketDTONullParameters() {
        TicketDTO ticketDTO = new TicketDTO(null, null, null);

        Set<ConstraintViolation<TicketDTO>> violations = validator.validate(ticketDTO);

        assertEquals(3, violations.size());
    }
}
