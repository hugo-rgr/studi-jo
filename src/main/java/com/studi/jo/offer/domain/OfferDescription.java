package com.studi.jo.offer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.owasp.encoder.Encode;

import javax.validation.constraints.Size;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class OfferDescription {

    @Column(name = "offer_description")
    @Size(max = 60, message = "Offer description length cannot be greater than 60 characters")
    private String value;

    public OfferDescription(String value){
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
