package org.arjun.jwt.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by arjuns on 22/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JOSEToken {
    private Map<String, String> tokens = new LinkedHashMap<>();

    public void addToken(String name, String value) {
        tokens.put(name, value);
    }

    public Map<String, String> getTokens() {
        return tokens;
    }
}
