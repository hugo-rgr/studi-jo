package com.studi.jo.ticket.domain;

import com.studi.jo.offer.domain.OfferName;
import com.studi.jo.purchase.domain.PurchaseKey;
import com.studi.jo.user.domain.UserKey;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Embedded
    OfferName offerName;

    @Embedded
    UserKey userKey;

    @Embedded
    PurchaseKey purchaseKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "validity_status")
    TicketValidityStatus validityStatus;

}
