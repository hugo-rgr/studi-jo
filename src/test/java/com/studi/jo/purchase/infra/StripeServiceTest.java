package com.studi.jo.purchase.infra;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StripeServiceTest {

    @InjectMocks
    private StripeService stripeService;

    @BeforeEach
    void setUp() {
        Stripe.apiKey = "sk_test_51PG5i9EP5310HSrvyaOMQiMAgqrN5hsPLMGDxTdcISBIwYGDpBS3CxiaEqdzfB9D4nju1I8iMEbPksJEgwnm4awo005JCY49Zv";
    }

    @Test
    void testChargeCreditCard() throws StripeException {
        double testAmount = 10.0;
        String testToken = "tok_visa";

        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount((long) (testAmount * 100))
                .setCurrency("eur")
                .setDescription("Test charge")
                .setSource(testToken)
                .build();

        Charge result = stripeService.chargeCreditCard(testToken, testAmount);
        assertEquals("succeeded", result.getStatus());
        assertEquals(1000.0, result.getAmount().doubleValue());
    }
}
