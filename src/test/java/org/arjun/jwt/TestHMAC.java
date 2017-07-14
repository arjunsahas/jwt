package org.arjun.jwt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
 * Created by arjuns on 22/6/17.
 */
public class TestHMAC {


    public static void main(
            String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        JOSEToken joseToken = new JOSEBuilder().
                addObjectType("JWT").
                addAlgorithm(JWSAlgorithm.HS256.name()).build();

        Claims claims = new ClaimsBuilder().
                addIssuer("arjun").
                addAudience("wm").
                addJwtID("ssss").
                addClaim("dummy", "aaa").build();


        JWTToken jwtToken = new JWTTokenBuilder().addJOSEToken(joseToken).addClaimsSet(claims).build();

        String randomId = UUID.randomUUID().toString();
        System.out.println(randomId);

        Signer signer = new HMACSigner(randomId);
        JWSSignedToken jwsSignedToken = new JWSSignedToken(jwtToken, signer);
        String signedJWTToken = jwsSignedToken.generateSignedToken();

        SignatureValidator signatureValidator = new HMACSignatureValidator();
        JWTValidator jwtValidator = new JWTValidatorImpl(signatureValidator);
        try {
            System.out.println(jwtValidator.verify(signedJWTToken, signer));
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

}
