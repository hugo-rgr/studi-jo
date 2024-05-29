package com.studi.jo.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Password {

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password cannot be null or empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*+-]).{8,20}$",
            message = "Password length should be 8-20 characters and should include at least one upper case letter, one lower case letter, one digit, and one special character.")
    private String value;

    public Password(String value){
        this.value = encode(value);
    }

    protected String encode(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public void setValue(String value){
        this.value = encode(value);
    }
}
