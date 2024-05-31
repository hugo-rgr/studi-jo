package com.studi.jo.offer.infra;

import com.studi.jo.offer.domain.Offer;
import com.studi.jo.offer.domain.OfferName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByOrderByIdAsc();

    Optional<Offer> findByName(OfferName name);
}
