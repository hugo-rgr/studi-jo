package com.studi.jo.controllers;

import com.studi.jo.ticket.domain.Ticket;
import com.studi.jo.ticket.service.TicketService;
import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.UserKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/export/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> getTicketsPDF(@RequestParam List<Long> id) {
        try {
            // Retrieve the first ticket if the right tickets are associated with the right user (for security)
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserKey userKey = currentUser.getUserKey();

            logger.info("Fetching ticket with ID: {}", id.get(0));
            Ticket firstTicket = ticketService.getTicketById(id.get(0));
            if (!firstTicket.getUserKey().equals(userKey)) {
                logger.warn("User key does not match for user: {}. Unauthorized to generate PDF for these tickets.", userKey);
                throw new IllegalArgumentException("User key does not match. Unauthorized to generate PDF for these tickets.");
            }
            logger.info("Generating PDF for tickets with IDs: {}", id);
            byte[] pdfContent = ticketService.generateTicketsPDF(id);
            return ResponseEntity.ok(pdfContent);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
