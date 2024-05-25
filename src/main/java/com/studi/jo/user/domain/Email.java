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
public class Email {

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email cannot be null or empty")
    @Size(max = 50, message = "Email length cannot be greater than 50 characters")
    @javax.validation.constraints.Email(message = "Email must be valid")
    private String value;

    @JsonCreator
    public Email(String value){
        this.value = validateAndSanitize(value);
    }

    private String validateAndSanitize(String value){
        return Encode.forHtml(value);
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

