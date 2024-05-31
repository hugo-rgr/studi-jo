package com.studi.jo.ticket.infra;

import com.studi.jo.ticket.domain.Ticket;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class TicketPDFService {

    public byte[] createTicketsPDF(List<Ticket> tickets) throws Exception {
        QRCodeService qrCodeService = new QRCodeService();

        PDDocument document = new PDDocument();

        for (Ticket ticket : tickets) {
            byte[] qrCode = qrCodeService.generateQRCodeImage(ticket.getId(), ticket.getUserKey().getValue(), ticket.getPurchaseKey().getValue());

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(ticket.getOfferName());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Votre e-ticket pour les Jeux Olympiques de Paris 2024");
            contentStream.endText();

            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, qrCode, "QR Code");
            contentStream.drawImage(pdImage, 100, 500, 150, 150);

            contentStream.close();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }
}
