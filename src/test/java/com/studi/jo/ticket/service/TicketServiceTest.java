package com.studi.jo.ticket.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.ticket.domain.*;
import com.studi.jo.user.domain.UserKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketPDFService ticketPDFService;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void testCreateTicket() {
        TicketDTO ticketDTO = new TicketDTO(new OfferName("Offre duo"), new UserKey(), new PurchaseKey());
        Ticket ticket = ticketDTO.toTicket();
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket createdTicket = ticketService.createTicket(ticketDTO);

        assertEquals("Offre duo", createdTicket.getOfferName());
    }

    @Test
    public void testCreateTickets() {
        TicketDTO ticketDTO1 = new TicketDTO(new OfferName("Offre solo"), new UserKey(), new PurchaseKey());
        TicketDTO ticketDTO2 = new TicketDTO(new OfferName("Offre duo"), new UserKey(), new PurchaseKey());
        List<TicketDTO> ticketDTOs = Arrays.asList(ticketDTO1, ticketDTO2);
        List<Ticket> tickets = ticketDTOs.stream().map(TicketDTO::toTicket).collect(Collectors.toList());
        when(ticketRepository.saveAll(anyList())).thenReturn(tickets);

        List<Long> ticketIds = ticketService.createTickets(ticketDTOs);

        assertEquals(2, ticketIds.size());
    }

    @Test
    public void testGenerateTicketsPDF() throws Exception {
        Ticket ticket = new Ticket(1L, "Offre duo", new UserKey(), new PurchaseKey(), TicketValidityStatus.VALID);
        List<Long> ticketIds = Arrays.asList(1L);
        when(ticketRepository.findAllById(ticketIds)).thenReturn(Arrays.asList(ticket));
        when(ticketPDFService.createTicketsPDF(anyList())).thenReturn(new byte[0]);

        byte[] pdfBytes = ticketService.generateTicketsPDF(ticketIds);

        assertNotNull(pdfBytes);
    }

    @Test
    public void testValidGetTicketById() {
        Ticket ticket = new Ticket(1L, "Offre duo", new UserKey(), new PurchaseKey(), TicketValidityStatus.VALID);
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        Ticket foundTicket = ticketService.getTicketById(1L);

        assertEquals("Offre duo", foundTicket.getOfferName());
    }
}
