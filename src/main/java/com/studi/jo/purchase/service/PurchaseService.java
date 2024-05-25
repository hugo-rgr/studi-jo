package com.studi.jo.purchase.service;

import com.studi.jo.purchase.domain.Purchase;
import com.studi.jo.purchase.domain.PurchaseDTO;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    protected Purchase savePurchase(Purchase purchase){
        return purchaseRepository.save(purchase);
    }

    public Purchase createPurchase(PurchaseDTO purchaseDTO) {
        return savePurchase(purchaseDTO.toPurchase());
    }
}
