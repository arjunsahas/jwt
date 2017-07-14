package org.arjun.jwt;

/**
 * Created by arjuns on 26/6/17.
 */
public enum JWSAlgorithm {
    HS256("HmacSHA256"),
    //    RS256("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
    RS256("SHA256withRSA"),
    RS512("SHA512withRSA"),
    ES256("SHA256withECDSA"),
    ES384("SHA384withECDSA"),
    ES512("SHA512withECDSA");

    private String algorithm;

    JWSAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

}
