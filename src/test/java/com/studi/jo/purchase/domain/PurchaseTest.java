package com.studi.jo.purchase.domain;

import com.studi.jo.common.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPurchase() {
        PurchaseKey purchaseKey = new PurchaseKey();
        List<Long> listTicketIds = Arrays.asList(1L, 2L, 3L);
        Price totalPrice = new Price(BigDecimal.valueOf(10.0));
        Purchase purchase = new Purchase(null, LocalDateTime.now(), purchaseKey, listTicketIds, totalPrice);

        Set<ConstraintViolation<Purchase>> violations = validator.validate(purchase);

        assertTrue(violations.isEmpty());
    }
}
