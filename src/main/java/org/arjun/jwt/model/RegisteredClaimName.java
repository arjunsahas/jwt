package org.arjun.jwt.model;

/**
 * Created by arjuns on 22/6/17.
 */
public enum RegisteredClaimName {
    ISSUER("iss"),
    SUBJECT("sub"),
    AUDIENCE("aud"),
    EXPIRATION_TIME("exp"),
    NOT_BEFORE("nbf"),
    ISSUED_AT("iat"),
    JWT_ID("jti");

    private String name;

    RegisteredClaimName(String jsonName) {
        this.name = jsonName;
    }

    public String getName() {
        return name;
    }
}
