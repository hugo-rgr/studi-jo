package com.studi.jo.controllers;

import com.studi.jo.offer.domain.Offer;
import com.studi.jo.offer.domain.OfferDTO;
import com.studi.jo.offer.infra.OfferService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offer")
@Validated
public class OfferController {

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @Autowired
    private OfferService offerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable Long id) {
        try {
            Offer offer = offerService.getOfferById(id);
            return ResponseEntity.ok(offer);
        } catch (EntityNotFoundException e) {
            logger.error("Error retrieving offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error retrieving offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: Could not retrieve offer. " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOffers() {
        try {
            List<Offer> offers = offerService.getAllOffers();
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: Could not retrieve offers. " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createOffer(@Valid @RequestBody OfferDTO offerDTO) {
        logger.info("Creating new offer: {}", offerDTO);
        try {
            Offer newOffer = offerService.createOffer(offerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOffer);
        } catch (Exception e) {
            logger.error("Error creating offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: Could not create offer. " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Long id, @Valid @RequestBody OfferDTO offerDTO) {
        logger.info("Updating offer with id: {}", id);
        try {
            Offer updatedOffer = offerService.updateOffer(id, offerDTO);
            return ResponseEntity.ok(updatedOffer);
        } catch (EntityNotFoundException e) {
            logger.error("Error updating offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: Could not update offer. " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        logger.info("Deleting offer with id: {}", id);
        try {
            offerService.deleteOffer(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            logger.error("Error deleting offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: Could not delete offer. " + e.getMessage());
        }
    }
}
