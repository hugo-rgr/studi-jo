package com.studi.jo.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.studi.jo.purchase.domain.Purchase;
import com.studi.jo.purchase.domain.PurchaseDTO;
import com.studi.jo.purchase.service.PurchaseService;
import com.studi.jo.purchase.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create")
    public Purchase createPurchase(@RequestBody PurchaseDTO purchaseDTO) {
        return purchaseService.createPurchase(purchaseDTO);
    }

    @PostMapping("/charge")
    public Map<String, String> chargeCard(@RequestBody Map<String, Object> request) throws StripeException {
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

        Charge charge = stripeService.chargeCreditCard("tok_visa", amount);
        Map<String, String> response = new HashMap<>();
        response.put("status", charge.getStatus());
        response.put("invoice", charge.getInvoice());
        return response;
    }

}
