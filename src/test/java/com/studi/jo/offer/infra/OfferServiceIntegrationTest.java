package com.studi.jo.offer.infra;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.studi.jo.offer.domain.Offer;
import com.studi.jo.offer.domain.OfferDescription;
import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.offer.domain.OfferDTO;
import com.studi.jo.common.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OfferServiceIntegrationTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;

    @Test
    public void testCreateOffer() {
        OfferDTO offerDTO = new OfferDTO(new OfferName("Offer Name"), new Price(new BigDecimal("24.99")), new OfferDescription("Offer Description"));
        Offer offer = offerDTO.toOffer();
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        Offer createdOffer = offerService.createOffer(offerDTO);

        assertEquals("Offer Name", createdOffer.getName().getValue());
    }

    @Test
    public void testUpdateOffer() {
        OfferDTO offerDTO = new OfferDTO(new OfferName("Offer Name"), new Price(new BigDecimal("24.99")), new OfferDescription("Offer Description"));
        Offer offer = offerDTO.toOffer();
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        Offer updatedOffer = offerService.updateOffer(1L, offerDTO);

        assertEquals("Offer Name", updatedOffer.getName().getValue());
    }

    @Test
    public void testUpdateOfferThrowsExceptionWhenNotFound() {
        OfferDTO offerDTO = new OfferDTO(new OfferName("Offer Name"), new Price(new BigDecimal("24.99")), new OfferDescription("Offer Description"));
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            offerService.updateOffer(1L, offerDTO);
        });

        assertEquals("ERROR: Offer with id 1 not found.", thrown.getMessage());
    }

    @Test
    public void testUpdateOfferIncrementSales() {
        OfferDTO offerDTO = new OfferDTO(new OfferName("Offer Name"), new Price(new BigDecimal("24.99")), new OfferDescription("Offer Description"));
        Offer offer = offerDTO.toOffer();
        offer.setSalesNumber(5);
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        Offer updatedOffer = offerService.updateOfferIncrementSales(offerDTO.getName(), 3);

        assertEquals(8, updatedOffer.getSalesNumber());
    }

    @Test
    public void testGetOfferById() {
        OfferDTO offerDTO = new OfferDTO(new OfferName("Offer Name"), new Price(new BigDecimal("24.99")), new OfferDescription("Offer Description"));
        Offer offer = offerDTO.toOffer();
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offer));

        Offer foundOffer = offerService.getOfferById(1L);

        assertEquals("Offer Name", foundOffer.getName().getValue());
    }

    @Test
    public void testGetAllOffers() {
        OfferDTO offerDTO = new OfferDTO(new OfferName("Offer Name"), new Price(new BigDecimal("24.99")), new OfferDescription("Offer Description"));
        List<Offer> offers = Arrays.asList(offerDTO.toOffer(), offerDTO.toOffer());
        when(offerRepository.findAllByOrderByIdAsc()).thenReturn(offers);

        List<Offer> allOffers = offerService.getAllOffers();

        assertEquals(2, allOffers.size());
    }

    @Test
    public void testDeleteOffer() {
        offerService.deleteOffer(1L);

        verify(offerRepository, times(1)).deleteById(1L);
    }
}


