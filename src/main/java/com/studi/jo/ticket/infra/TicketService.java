package com.studi.jo.ticket.infra;

import com.studi.jo.ticket.domain.Ticket;
import com.studi.jo.ticket.domain.TicketDTO;
import com.studi.jo.ticket.domain.TicketValidityStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    protected Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(TicketDTO ticketDTO) {
        return saveTicket(ticketDTO.toTicket());
    }

    public void updateTicketValidityStatus(Long id, TicketValidityStatus currentTicketValidityStatus, TicketValidityStatus action){
        if (currentTicketValidityStatus == TicketValidityStatus.VALID) {
            LocalDateTime currentDate = LocalDateTime.now();
            Ticket currentTicket = this.getTicketById(id);
            if (currentDate.isAfter(LocalDateTime.parse("2024-07-26T14:30:00")) && currentDate.isBefore(LocalDateTime.parse("2024-08-11T23:59:59"))) {
                currentTicket.setValidityStatus(action);
                ticketRepository.save(currentTicket);
            } else if (currentDate.isAfter(LocalDateTime.parse("2024-08-11T23:59:59"))) {
                currentTicket.setValidityStatus(action);
                ticketRepository.save(currentTicket);
            }
        }
    }

    protected Ticket getTicketById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (!ticket.isPresent()) {
            throw new EntityNotFoundException("ERROR: Offer with id " + id + " not found.");
        }
        return ticket.get();
    }

    // Runs at 11:59:59 PM on August 11, 2024, to set every remaining tickets as expired
    @Scheduled(cron = "59 59 23 11 8 2024")
    @Transactional
    public void updateExpiredTickets() {
        OffsetDateTime now = OffsetDateTime.now();
        ticketRepository.updateTicketValidityStatusToExpired();
    }
}
