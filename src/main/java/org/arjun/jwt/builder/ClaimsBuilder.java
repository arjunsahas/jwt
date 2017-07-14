package org.arjun.jwt.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.arjun.jwt.model.Claims;
import org.arjun.jwt.model.RegisteredClaimName;

/**
 * Created by arjuns on 22/6/17.
 */
public class ClaimsBuilder {
    private String issuer;
    private String subject;
    private String audience;
    private String expirationTime;
    private String notBefore;
    private String issuedAt;
    private String jwtID;

    private Map<String, String> keyValueMap = new LinkedHashMap<>();

    public ClaimsBuilder addIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public ClaimsBuilder addSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public ClaimsBuilder addAudience(String audience) {
        this.audience = audience;
        return this;
    }

    public ClaimsBuilder addExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public ClaimsBuilder AddNotBefore(String notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    public ClaimsBuilder addIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public ClaimsBuilder addJwtID(String jwtID) {
        this.jwtID = jwtID;
        return this;
    }

    public ClaimsBuilder addClaim(String key, String value) {
        keyValueMap.put(key, value);
        return this;
    }

    public Claims build() {
        Claims claims = new Claims();
        buildMap(claims, RegisteredClaimName.ISSUER, issuer);
        buildMap(claims, RegisteredClaimName.SUBJECT, subject);
        buildMap(claims, RegisteredClaimName.AUDIENCE, audience);
        buildMap(claims, RegisteredClaimName.ISSUED_AT, issuedAt);
        buildMap(claims, RegisteredClaimName.JWT_ID, jwtID);
        buildMap(claims, RegisteredClaimName.EXPIRATION_TIME, expirationTime);
        buildMap(claims, RegisteredClaimName.NOT_BEFORE, notBefore);

        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            claims.addClaim(entry.getKey(), entry.getValue());
        }
        return claims;
    }

    private void buildMap(Claims claims, RegisteredClaimName claimName, final String value) {
        if (StringUtils.isNotBlank(value)) {
            claims.addClaim(claimName.getName(), value);
        }
    }

}
