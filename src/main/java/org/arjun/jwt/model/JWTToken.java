package org.arjun.jwt.model;

import org.arjun.jwt.model.Claims;
import org.arjun.jwt.model.JOSEToken;

/**
 * Created by arjuns on 23/6/17.
 */
public class JWTToken {
    private Claims claimsSet;
    private org.arjun.jwt.model.JOSEToken JOSEToken;

    public Claims getClaimsSet() {
        return claimsSet;
    }

    public void setClaimsSet(final Claims claimsSet) {
        this.claimsSet = claimsSet;
    }

    public JOSEToken getJOSEToken() {
        return JOSEToken;
    }

    public void setJOSEToken(final JOSEToken JOSEToken) {
        this.JOSEToken = JOSEToken;
    }
}
