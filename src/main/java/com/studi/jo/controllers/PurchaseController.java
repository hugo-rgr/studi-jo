package com.studi.jo.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.studi.jo.common.domain.Price;
import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.offer.infra.OfferService;
import com.studi.jo.purchase.domain.Purchase;
import com.studi.jo.purchase.domain.PurchaseDTO;
import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.purchase.service.PurchaseService;
import com.studi.jo.purchase.service.StripeService;
import com.studi.jo.ticket.domain.TicketDTO;
import com.studi.jo.ticket.infra.TicketService;
import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.UserKey;
import com.studi.jo.user.infra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private OfferService offerService;

    @PostMapping("/create")
    public Purchase createPurchase(@RequestBody PurchaseDTO purchaseDTO) {
        return purchaseService.createPurchase(purchaseDTO);
    }

    @PostMapping("/charge")
    public Map<String, Object> chargeCard(@RequestBody Map<String, Object> request) throws StripeException {
        Object amountObject = request.get("amount");
        if (amountObject == null) {
            throw new IllegalArgumentException("Amount is required");
        }

        double amount;
        if (amountObject instanceof Number) {
            amount = ((Number) amountObject).doubleValue();
        } else {
            amount = Double.parseDouble(amountObject.toString());
        }

        // Charge the credit card
        Charge charge = stripeService.chargeCreditCard("tok_visa", amount);
        Map<String, Object> response = new HashMap<>();
        response.put("status", charge.getStatus());

        if (!"succeeded".equals(charge.getStatus())) { // in case the charge is not successful, to prevent ticket and purchase creation
            return response;
        }

        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //String email = userDetails.getUsername();
        User currentUser = userService.getUserByEmail("admin@example.com");
        UserKey userKey = currentUser.getUserKey();
        PurchaseKey purchaseKey = new PurchaseKey();

        List<Map<String, Object>> offers = (List<Map<String, Object>>) request.get("offers");
        List<TicketDTO> ticketDTOs = new ArrayList<>();
        offers.forEach(offer -> {
            String name = (String) offer.get("name");
            OfferName offerName = new OfferName(name);
            int quantity = ((Number) offer.get("quantity")).intValue();
            for (int i = 0; i < quantity; i++) {
                TicketDTO ticketDTO = new TicketDTO(offerName, userKey, purchaseKey);
                ticketDTOs.add(ticketDTO);
            }
            offerService.updateOfferIncrementSales(offerName, quantity);
        });
        List<Long> ticketIds = ticketService.createTickets(ticketDTOs);

        Purchase purchase = purchaseService.createPurchase(new PurchaseDTO(ticketIds, new Price(BigDecimal.valueOf(amount))));

        response.put("ticketIds", ticketIds);
        return response;
    }
}
