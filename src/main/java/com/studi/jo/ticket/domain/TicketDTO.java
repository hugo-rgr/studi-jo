package com.studi.jo.ticket.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.user.domain.UserKey;
import lombok.Getter;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Value
public class TicketDTO {

    @Valid
    @NotNull
    OfferName offerName;

    @Valid
    @NotNull
    UserKey userKey;

    @Valid
    @NotNull
    PurchaseKey purchaseKey;

    @JsonCreator
    public TicketDTO(@JsonProperty("offerName") @Valid @NotNull OfferName offerName,
                     @JsonProperty("userKey") @Valid @NotNull UserKey userKey,
                     @JsonProperty("purchaseKey") @Valid @NotNull PurchaseKey purchaseKey) {
        this.offerName = offerName;
        this.userKey = userKey;
        this.purchaseKey = purchaseKey;
    }

    public Ticket toTicket() {
        return new Ticket(
                null,
                this.offerName.getValue(),
                this.userKey,
                this.purchaseKey,
                TicketValidityStatus.VALID
        );
    }
}
