package com.studi.jo.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.studi.jo.common.domain.Price;
import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.offer.infra.OfferService;
import com.studi.jo.purchase.domain.Purchase;
import com.studi.jo.purchase.domain.PurchaseDTO;
import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.purchase.infra.PurchaseService;
import com.studi.jo.purchase.infra.StripeService;
import com.studi.jo.ticket.domain.TicketDTO;
import com.studi.jo.ticket.infra.TicketService;
import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.UserKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private OfferService offerService;

    @PostMapping("/charge")
    public ResponseEntity<Map<String, Object>> chargeCard(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Object amountObject = request.get("amount");
            if (amountObject == null) {
                logger.error("Amount is required but not provided");
                throw new IllegalArgumentException("Amount is required");
            }

            double amount;
            if (amountObject instanceof Number) {
                amount = ((Number) amountObject).doubleValue();
            } else {
                amount = Double.parseDouble(amountObject.toString());
            }

            logger.info("Charging credit card with amount: {}", amount);
            Charge charge = stripeService.chargeCreditCard("tok_visa", amount);
            response.put("status", charge.getStatus());

            if (!"succeeded".equals(charge.getStatus())) {
                logger.warn("Charge did not succeed with status: {}", charge.getStatus());
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(response);
            }

            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

            purchaseService.createPurchase(new PurchaseDTO(ticketIds, new Price(BigDecimal.valueOf(amount))));

            response.put("ticketIds", ticketIds);
            logger.info("Purchase created successfully with ticket IDs: {}", ticketIds);
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            logger.error("Stripe error occurred: {}", e.getMessage(), e);
            response.put("error", "Stripe error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage(), e);
            response.put("error", "Invalid request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
            response.put("error", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
