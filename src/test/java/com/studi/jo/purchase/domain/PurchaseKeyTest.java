package com.studi.jo.purchase.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class PurchaseKeyTest {

    @Test
    public void testValidPurchaseKeyUniqueness() {
        PurchaseKey purchaseKey1 = new PurchaseKey();
        PurchaseKey purchaseKey2 = new PurchaseKey();
        assertNotEquals(purchaseKey1.getValue(), purchaseKey2.getValue(), "Purchase keys should be unique");
    }
}
