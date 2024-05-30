package com.studi.jo.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Digits;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Price {

    @Column(name = "price", nullable = false)
    @Digits(integer = 10, fraction = 2, message = "Price must have up to two decimal numbers")
    @Min(value = 0, message = "Price must be greater or equal to zero")
    @Max(value = 500, message = "Price must be inferior to 500")
    private BigDecimal value;

    public Price(BigDecimal value){
        this.value = forceFloorRounding(value);
    }

    protected BigDecimal forceFloorRounding(BigDecimal value){
        return value.setScale(2, RoundingMode.DOWN);
    }

    @JsonValue
    public BigDecimal getValue(){
        return value;
    }

    public void setValue(BigDecimal value){
        this.value = forceFloorRounding(value);
    }

    @JsonCreator
    public static Price fromValue(BigDecimal value){
        return new Price(value);
    }
}
