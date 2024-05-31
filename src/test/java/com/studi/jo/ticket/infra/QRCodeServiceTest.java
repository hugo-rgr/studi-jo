package com.studi.jo.ticket.infra;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QRCodeServiceTest {

    @Test
    public void testGenerateQRCodeImage() throws Exception {
        long ticketId = 42L;
        String userKey = "userKeyTest";
        String purchaseKey = "purchaseKeyTest";

        QRCodeService qrCodeService = new QRCodeService();
        byte[] qrCodeImage = qrCodeService.generateQRCodeImage(ticketId, userKey, purchaseKey);

        assertTrue(qrCodeImage.length > 0);
    }

    @Test
    public void testCombineKeys() {
        long ticketId = 42L;
        String userKey = "userKeyTest";
        String purchaseKey = "purchaseKeyTest";

        QRCodeService qrCodeService = new QRCodeService();
        String combinedKeys = qrCodeService.combineKeys(ticketId, userKey, purchaseKey);

        assertEquals(ticketId + userKey + purchaseKey, combinedKeys);
    }
}
