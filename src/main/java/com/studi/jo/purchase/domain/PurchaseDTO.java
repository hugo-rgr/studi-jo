package com.studi.jo.purchase.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studi.jo.common.domain.Price;
import lombok.Getter;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Value
public class PurchaseDTO {

    @Valid
    @NotNull
    List<Long> listTicketIds;

    @Valid
    @NotNull
    Price totalPrice;

    @JsonCreator
    public PurchaseDTO(@JsonProperty("listTicketIds") @Valid @NotNull List<Long> listTicketIds,
                       @JsonProperty("totalPrice") @Valid @NotNull Price totalPrice){
        this.listTicketIds = listTicketIds;
        this.totalPrice = totalPrice;
    }

    public Purchase toPurchase(){
        return new Purchase(
                null,
                LocalDateTime.now(),
                new PurchaseKey(),
                this.listTicketIds,
                this.totalPrice
        );
    }
}
