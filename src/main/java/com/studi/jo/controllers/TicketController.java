package com.studi.jo.controllers;

import com.studi.jo.ticket.domain.Ticket;
import com.studi.jo.ticket.domain.TicketDTO;
import com.studi.jo.ticket.infra.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    //@PreAuthorize("isAuthenticated()")
    public Ticket createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

}
