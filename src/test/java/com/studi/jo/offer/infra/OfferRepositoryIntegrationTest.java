package com.studi.jo.offer.infra;

import static org.junit.jupiter.api.Assertions.*;
import com.studi.jo.offer.domain.Offer;
import com.studi.jo.offer.domain.OfferDescription;
import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.common.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OfferRepositoryIntegrationTest {

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void testFindAllByOrderByIdAsc() {
        Offer offer1 = new Offer(null, new OfferName("Offer 1"), new OfferDescription("Description 1"), new Price(new BigDecimal("13.99")), 2);
        Offer offer2 = new Offer(null, new OfferName("Offer 2"), new OfferDescription("Description 2"), new Price(new BigDecimal("20.00")), 7);

        offerRepository.save(offer1);
        offerRepository.save(offer2);

        List<Offer> offers = offerRepository.findAllByOrderByIdAsc();

        assertNotNull(offers);
        assertEquals(2, offers.size());
        assertEquals("Offer 1", offers.get(0).getName().getValue());
        assertEquals("Offer 2", offers.get(1).getName().getValue());
    }
}
