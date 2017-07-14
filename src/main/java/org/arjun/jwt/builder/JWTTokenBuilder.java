package org.arjun.jwt.builder;

import org.arjun.jwt.model.Claims;
import org.arjun.jwt.model.JOSEToken;
import org.arjun.jwt.model.JWTToken;

/**
 * Created by arjuns on 23/6/17.
 */
public class JWTTokenBuilder {

    private JOSEToken josetoken;
    private Claims claimsSet;

    public JWTTokenBuilder addJOSEToken(JOSEToken joseToken) {
        this.josetoken = joseToken;
        return this;
    }

    public JWTTokenBuilder addClaimsSet(Claims claims) {
        this.claimsSet = claims;
        return this;
    }

    public JWTToken build() {
        JWTToken jwtToken = new JWTToken();
        jwtToken.setJOSEToken(josetoken);
        jwtToken.setClaimsSet(claimsSet);
        return jwtToken;
    }
}
