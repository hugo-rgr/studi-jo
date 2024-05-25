package com.studi.jo.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.owasp.encoder.Encode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class LastName {

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name cannot be null or empty")
    @Size(max = 50, message = "Last name length cannot be greater than 50 characters")
    String value;

    @JsonCreator
    public LastName(String value) {
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
