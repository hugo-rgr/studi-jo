package com.studi.jo.ticket.domain;

import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.user.domain.UserKey;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// Billets
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "offer name must not be null")
    String offerName;

    @Embedded
    UserKey userKey;

    @Embedded
    PurchaseKey purchaseKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "validity_status")
    @NotNull(message = "ticket validity status must not be null")
    TicketValidityStatus validityStatus;

}
