package org.arjun.jwt.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;

import org.arjun.jwt.JWSAlgorithm;
import org.arjun.jwt.JWSSignedToken;
import org.arjun.jwt.builder.ClaimsBuilder;
import org.arjun.jwt.builder.JOSEBuilder;
import org.arjun.jwt.builder.JWTTokenBuilder;
import org.arjun.jwt.exception.InvalidTokenException;
import org.arjun.jwt.model.Claims;
import org.arjun.jwt.model.JOSEToken;
import org.arjun.jwt.model.JWTToken;
import org.arjun.jwt.signer.HMACSigner;
import org.arjun.jwt.signer.Signer;
import org.arjun.jwt.validator.HMACSignatureValidator;
import org.arjun.jwt.validator.JWTValidator;
import org.arjun.jwt.validator.JWTValidatorImpl;
import org.arjun.jwt.validator.SignatureValidator;

/**
 * Created by arjuns on 30/6/17.
 */
public class AuthService {

    private Map<String, String> stringStringMap = new ConcurrentHashMap<>();

    public String authenticate(final String username, final String password, final HttpServletRequest req) {
        if (username.equalsIgnoreCase("arjun") && password.equals("arjun")) {
            JOSEToken joseToken = new JOSEBuilder().
                    addObjectType("JWT").
                    addAlgorithm(JWSAlgorithm.HS256.name()).build();

            String format = DateFormat.getInstance().format(new Date());
            int expirationTimeInSeconds = 60 *30;

            Claims claims = new ClaimsBuilder().
                    addIssuer(username).
                    addAudience("wm").
                    addJwtID("ssss").
                    addIssuedAt(format).
                    addExpirationTime(String.valueOf(expirationTimeInSeconds)).
                    addIssuer(req.getContextPath()).
                    build();

            JWTToken jwtToken = new JWTTokenBuilder().addJOSEToken(joseToken).addClaimsSet(claims).build();

            String secretKey = UUID.randomUUID().toString();
            Signer signer = new HMACSigner(secretKey);
            JWSSignedToken jwsSignedToken = new JWSSignedToken(jwtToken, signer);
            String signedJWTToken = jwsSignedToken.generateSignedToken();
            stringStringMap.put(signedJWTToken, secretKey);
            return signedJWTToken;
        } else {
            return null;
        }
    }

    public boolean verify(final String token, final HttpServletRequest req) {
        String secretKey = stringStringMap.get(token);
        SignatureValidator signatureValidator = new HMACSignatureValidator();
        JWTValidator jwtValidator = new JWTValidatorImpl(signatureValidator) {

            @Override
            protected boolean verifyIssuer(final String issuer) {
                return issuer.equals(req.getContextPath());
            }

            @Override
            protected boolean verifyIssuedAt(Date date) {
                return date.before(new Date());
            }

            @Override
            protected boolean verifyExpirationTime(Date date, int expirationTimeInSeconds) {
                return (date.getTime() + (expirationTimeInSeconds * 1000)) > new Date().getTime();
            }
        };
        Signer signer = new HMACSigner(secretKey);
        try {
            return jwtValidator.verify(token, signer);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
        return false;
    }
}
