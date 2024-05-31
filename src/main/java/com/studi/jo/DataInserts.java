package com.studi.jo;

import com.studi.jo.common.domain.Price;
import com.studi.jo.offer.domain.OfferDTO;
import com.studi.jo.offer.domain.OfferDescription;
import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.offer.infra.OfferService;
import com.studi.jo.purchase.domain.PurchaseDTO;
import com.studi.jo.purchase.infra.PurchaseService;
import com.studi.jo.ticket.domain.TicketDTO;
import com.studi.jo.ticket.infra.TicketService;
import com.studi.jo.user.domain.*;
import com.studi.jo.user.infra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInserts implements CommandLineRunner {

    private final UserService userService;
    private final OfferService offerService;
    private final TicketService ticketService;
    private final PurchaseService purchaseService;

    @Autowired
    public DataInserts(UserService userService, OfferService offerService, PurchaseService purchaseService, TicketService ticketService) {
        this.userService = userService;
        this.offerService = offerService;
        this.purchaseService = purchaseService;
        this.ticketService = ticketService;
    }

    public void dataInsertsForUser() {
        UserDTO userClient = new UserDTO(new FirstName("John"), new LastName("Doe"), new Email("client@example.com"), "Lorem5ipsum@+");
        userService.createUser(userClient, Role.CLIENT);

        UserDTO userEmployee = new UserDTO(new FirstName("Employee"), new LastName("Em"), new Email("employee@example.com"), "Lorem5ipsum@+");
        userService.createUser(userEmployee, Role.EMPLOYEE);

        UserDTO userAdmin = new UserDTO(new FirstName("Admin"), new LastName("Ad"), new Email("admin@example.com"), "Lorem5ipsum@+");
        userService.createUser(userAdmin, Role.ADMIN);
    }

    public void dataInsertsForOffer() throws Exception {
        OfferDTO offerSolo = new OfferDTO(new OfferName("Offre solo"), new Price(BigDecimal.valueOf(5.0)), null);
        offerService.createOffer(offerSolo);

        OfferDTO offerDuo = new OfferDTO(new OfferName("Offre duo"), new Price(BigDecimal.valueOf(9.0)), null);
        offerService.createOffer(offerDuo);

        OfferDTO offerFamily = new OfferDTO(new OfferName("Offre familiale"), new Price(BigDecimal.valueOf(16.0)), new OfferDescription("Pour 4 personnes"));
        offerService.createOffer(offerFamily);
    }

    public void dataInsertsForPurchase() {
        List<Long> purchaseSolo1L = new ArrayList<>();
        purchaseSolo1L.add(1L);
        purchaseSolo1L.add(2L);
        BigDecimal totalPrice = offerService.getOfferById(1L).getPrice().getValue().add(offerService.getOfferById(2L).getPrice().getValue());
        offerService.updateOfferIncrementSales(new OfferName("Offre solo"), 1);
        offerService.updateOfferIncrementSales(new OfferName("Offre duo"), 1);

        PurchaseDTO purchaseSolo1 = new PurchaseDTO(purchaseSolo1L, new Price(totalPrice));
        purchaseService.createPurchase(purchaseSolo1);
    }

    public void dataInsertsForTicket() throws Exception {
        TicketDTO ticket1 = new TicketDTO(offerService.getOfferById(1L).getName(), userService.getUserById(1L).getUserKey(), purchaseService.getPurchaseById(1L).getPurchaseKey());
        ticketService.createTicket(ticket1);

        TicketDTO ticket2 = new TicketDTO(offerService.getOfferById(2L).getName(), userService.getUserById(1L).getUserKey(), purchaseService.getPurchaseById(1L).getPurchaseKey());
        ticketService.createTicket(ticket2);
    }

    @Override
    public void run(String... args) throws Exception {
        dataInsertsForUser();
        dataInsertsForOffer();
        dataInsertsForPurchase();
        dataInsertsForTicket();
    }
}
