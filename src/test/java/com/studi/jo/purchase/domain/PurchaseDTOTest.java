package com.studi.jo.purchase.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.studi.jo.common.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PurchaseDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPurchaseDTO() {
        List<Long> listTicketIds = Arrays.asList(1L, 2L, 3L);
        Price totalPrice = new Price(BigDecimal.valueOf(10.00));
        PurchaseDTO purchaseDTO = new PurchaseDTO(listTicketIds, totalPrice);

        Set<ConstraintViolation<PurchaseDTO>> violations = validator.validate(purchaseDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidToPurchase() {
        List<Long> listTicketIds = Arrays.asList(1L, 2L, 3L);
        Price totalPrice = new Price(BigDecimal.valueOf(10.00));
        PurchaseDTO purchaseDTO = new PurchaseDTO(listTicketIds, totalPrice);

        Purchase purchase = purchaseDTO.toPurchase();

        assertEquals(listTicketIds, purchase.getListTicketIds());
        assertEquals(totalPrice, purchase.getTotalPrice());
        assertNotNull(purchase.getDateOfPurchase());
        assertNotNull(purchase.getPurchaseKey());
    }

    @Test
    public void testInvalidPurchaseDTONullParameters() {
        PurchaseDTO purchaseDTO = new PurchaseDTO(null, null);

        Set<ConstraintViolation<PurchaseDTO>> violations = validator.validate(purchaseDTO);

        assertEquals(2, violations.size());
    }
}

