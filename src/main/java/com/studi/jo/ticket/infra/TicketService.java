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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketPDFService ticketPDFService;

    public TicketService(TicketRepository ticketRepository, TicketPDFService ticketPDFService){
        this.ticketRepository = ticketRepository;
        this.ticketPDFService = ticketPDFService;
    }

    protected Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(TicketDTO ticketDTO) {
        return saveTicket(ticketDTO.toTicket());
    }

    @Transactional
    public List<Long> createTickets(List<TicketDTO> ticketDTOs) {
        List<Ticket> tickets = ticketDTOs.stream()
                .map(TicketDTO::toTicket)
                .collect(Collectors.toList());
        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);
        return savedTickets.stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
    }

    public byte[] generateTicketsPDF(List<Long> listIds) throws Exception {
        List<Ticket> tickets = ticketRepository.findAllById(listIds);
        if (!tickets.isEmpty()) {
            return ticketPDFService.createTicketsPDF(tickets);
        }
        return null;
    }

    public void updateTicketValidityStatus(Long id, TicketValidityStatus currentTicketValidityStatus){
        if (currentTicketValidityStatus == TicketValidityStatus.VALID) {
            LocalDateTime currentDate = LocalDateTime.now();
            Ticket currentTicket = this.getTicketById(id);
            if (currentDate.isAfter(LocalDateTime.parse("2024-07-26T14:30:00")) && currentDate.isBefore(LocalDateTime.parse("2024-08-11T23:59:59"))) {
                currentTicket.setValidityStatus(TicketValidityStatus.USED);
                ticketRepository.save(currentTicket);
            } else if (currentDate.isAfter(LocalDateTime.parse("2024-08-11T23:59:59"))) {
                currentTicket.setValidityStatus(TicketValidityStatus.EXPIRED);
                ticketRepository.save(currentTicket);
            }
        }
    }

    public Ticket getTicketById(Long id) {
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
