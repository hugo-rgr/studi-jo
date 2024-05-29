package com.studi.jo.purchase.service;

import com.studi.jo.purchase.domain.Purchase;
import com.studi.jo.purchase.domain.PurchaseDTO;
import com.studi.jo.purchase.domain.Purchase;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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

    public Purchase getPurchaseById(Long id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (!purchase.isPresent()) {
            throw new EntityNotFoundException("ERROR: Purchase with id " + id + " not found.");
        }
        return purchase.get();
    }
}
