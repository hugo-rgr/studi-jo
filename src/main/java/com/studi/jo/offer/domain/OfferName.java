package com.studi.jo.offer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.owasp.encoder.Encode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class OfferName {

    @Column(name = "offer_name", nullable = false, unique = true)
    @NotBlank(message = "Offer name cannot be null or empty")
    @Size(max = 20, message = "Offer name length cannot be greater than 20 characters")
    private String value;

    public OfferName(String value){
        this.value = validateAndSanitize(value);
    }

    private String validateAndSanitize(String value){
        String newValue = value.trim();
        newValue = newValue.replaceAll("\\s+", " "); // remove multiple spaces between words and replace with single space
        newValue = Encode.forHtml(newValue).replace("&#39;", "'");; // protect against HTML injections (allow single quotes)
        return newValue;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public void setValue(String value){
        this.value = validateAndSanitize(value);
    }
}
