package com.studi.jo.ticket.infra;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QRCodeService {

    private final int QR_CODE_WIDTH = 200;
    private final int QR_CODE_HEIGHT = 200;

    public byte[] generateQRCodeImage(long ticketId, String userKey, String purchaseKey) throws Exception {
        String combinedKeys = combineKeys(ticketId, userKey, purchaseKey);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(combinedKeys, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    public byte[] generateQRCodeImage(String userKey, String purchaseKey) throws Exception {
        String combinedKeys = userKey + purchaseKey;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(combinedKeys, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    private String combineKeys(long ticketId, String userKey, String purchaseKey) {
        return ticketId + userKey + purchaseKey;
    }
}

