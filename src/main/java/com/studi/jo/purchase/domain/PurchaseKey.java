package com.studi.jo.purchase.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.security.SecureRandom;
import java.util.Base64;

@Embeddable
@EqualsAndHashCode
@ToString
public class PurchaseKey {

    private static final BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(24);
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Column(name = "purchase_key", nullable = false)
    String value;

    @JsonCreator
    public PurchaseKey() {
        this.value = generateKey();
    }

    private static String generateKey() {
        byte[] keyBytes = keyGenerator.generateKey();
        return base64Encoder.encodeToString(keyBytes);
    }

    @JsonValue
    public String getValue(){
        return value;
    }
}
