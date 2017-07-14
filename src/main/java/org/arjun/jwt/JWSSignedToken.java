package org.arjun.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.arjun.jwt.model.JOSEHeader;
import org.arjun.jwt.model.JWTToken;
import org.arjun.jwt.signer.Signer;
import org.arjun.jwt.util.JsonUtils;
import org.arjun.jwt.validator.JWTValidator;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by arjuns on 26/6/17.
 */
public class JWSSignedToken {
    public static final String DOT = ".";
    private final JWTToken jwtToken;
    private final Signer signer;

    public JWSSignedToken(final JWTToken jwtToken, Signer signer) {
        this.jwtToken = jwtToken;
        this.signer = signer;
    }

    public String generateSignedToken() {
        StringBuilder builder = new StringBuilder();
        Map<String, String> joseTokens = jwtToken.getJOSEToken().getTokens();
        try {
            String base64EncodedJoseToken = getEncodedToken(joseTokens);
            String base64EncodedClaim = getEncodedToken(jwtToken.getClaimsSet().getClaims());
            builder.append(base64EncodedJoseToken).append(DOT).append(base64EncodedClaim);
            String signedToken = signer.sign(
                    JWSAlgorithm.valueOf(joseTokens.get(JOSEHeader.ALGORITHM.getName())).getAlgorithm(),
                    builder.toString());
            builder.append(DOT).append(signedToken);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    private String getEncodedToken(final Map<String, String> joseTokens) throws JsonProcessingException {
        String joseTokenString = JsonUtils.toJsonString(joseTokens);
        System.out.println(joseTokenString);
        return Base64.encodeBase64String(joseTokenString.getBytes(StandardCharsets.UTF_8));
    }

}
