package com.studi.jo.purchase.domain;

import com.studi.jo.common.domain.Price;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// Achats
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "purchase_date", nullable = false)
    LocalDateTime dateOfPurchase;

    @Embedded
    PurchaseKey purchaseKey;

    @ElementCollection
    @Column(name = "list_ticket_ids", nullable = false)
    List<Long> listTicketIds;

    @Embedded
    Price totalPrice; // Montant total

}
