package com.studi.jo.offer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studi.jo.common.domain.Price;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Value
public class OfferDTO {

    @Valid
    @NotNull
    OfferName name;

    @Valid
    OfferDescription description;

    @Valid
    @NotNull
    Price price;

    @JsonCreator
    public OfferDTO(
            @JsonProperty("name") @Valid @NotNull OfferName name,
            @JsonProperty("price") @Valid @NotNull Price price,
            @JsonProperty("description") @Valid OfferDescription description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Offer toOffer() {
        return new Offer(null, this.name, this.description, this.price, 0);
    }
}

