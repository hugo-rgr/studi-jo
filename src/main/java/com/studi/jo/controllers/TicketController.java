package com.studi.jo.controllers;

import com.studi.jo.ticket.domain.Ticket;
import com.studi.jo.ticket.domain.TicketDTO;
import com.studi.jo.ticket.infra.TicketService;
import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public Ticket createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @GetMapping(value = "/export/pdf", produces = "application/pdf")
    public byte[] getTicketsPDF(@RequestParam List<Long> id) throws Exception {
        // Retrieve the first ticket if the right tickets are associated with the right user (for security)
        //User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //UserKey userKey = currentUser.getUserKey();

        Ticket firstTicket = ticketService.getTicketById(id.get(0));
        //if (!firstTicket.getUserKey().equals(userKey)) {
        //    throw new IllegalArgumentException("User key does not match. Unauthorized to generate PDF for these tickets.");
        //}

        return ticketService.generateTicketsPDF(id);
    }

}
