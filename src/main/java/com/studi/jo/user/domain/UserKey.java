package com.studi.jo.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Base64;

@Embeddable
@EqualsAndHashCode
@ToString
public class UserKey {

    //private static final BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(24);
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Column(name = "user_key", nullable = false)
    String value;

    @JsonCreator
    public UserKey() {
        this.value = generateKey();
    }

    private static String generateKey() {
        //byte[] keyBytes = keyGenerator.generateKey();
        return base64Encoder.encodeToString(null); //TODO: implement
    }

    @JsonValue
    public String getValue(){
        return value;
    }
}
