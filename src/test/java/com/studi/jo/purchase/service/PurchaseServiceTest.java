package com.studi.jo.purchase.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.studi.jo.common.domain.Price;
import com.studi.jo.purchase.domain.Purchase;
import com.studi.jo.purchase.domain.PurchaseDTO;
import com.studi.jo.purchase.domain.PurchaseKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    public void testCreatePurchase() {
        PurchaseDTO purchaseDTO = new PurchaseDTO(Arrays.asList(1L, 2L, 3L), new Price(BigDecimal.valueOf(10.00)));
        Purchase purchase = new Purchase(1L, LocalDateTime.now(), new PurchaseKey(), Arrays.asList(1L, 2L, 3L), new Price(BigDecimal.valueOf(10.00)));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        Purchase createdPurchase = purchaseService.createPurchase(purchaseDTO);

        assertEquals(purchaseDTO.getListTicketIds(), createdPurchase.getListTicketIds());
        assertEquals(purchaseDTO.getTotalPrice(), createdPurchase.getTotalPrice());
    }

    @Test
    public void testValidGetPurchaseById() {
        Purchase purchase = new Purchase(1L, LocalDateTime.now(), new PurchaseKey(), Arrays.asList(1L, 2L, 3L), new Price(BigDecimal.valueOf(10.00)));
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));

        Purchase foundPurchase = purchaseService.getPurchaseById(1L);

        assertEquals(purchase.getDateOfPurchase(), foundPurchase.getDateOfPurchase());
    }

    @Test
    public void testInvalidGetPurchaseById() {
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            purchaseService.getPurchaseById(1L);
        });

        assertEquals("ERROR: Purchase with id 1 not found.", thrown.getMessage());
    }
}

