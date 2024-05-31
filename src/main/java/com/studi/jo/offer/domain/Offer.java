package com.studi.jo.offer.domain;

import com.studi.jo.common.domain.Price;
import javax.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    @Valid
    OfferName name;

    @Embedded
    @Valid
    OfferDescription description;

    @Embedded
    @Valid
    Price price;

    @Column(nullable = false)
    @Min(value = 0, message = "Offer sales number must be greater or equal to zero")
    int salesNumber;

}

