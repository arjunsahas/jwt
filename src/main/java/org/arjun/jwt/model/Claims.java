package org.arjun.jwt.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by arjuns on 22/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Claims {

    private Map<String, String> claims = new LinkedHashMap<>();

    public void addClaim(String name, String value) {
        claims.put(name, value);
    }

    public Map<String, String> getClaims() {
        return claims;
    }
}
