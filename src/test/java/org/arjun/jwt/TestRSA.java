package org.arjun.jwt;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.arjun.jwt.builder.ClaimsBuilder;
import org.arjun.jwt.builder.JOSEBuilder;
import org.arjun.jwt.builder.JWTTokenBuilder;
import org.arjun.jwt.exception.InvalidTokenException;
import org.arjun.jwt.model.Claims;
import org.arjun.jwt.model.JOSEToken;
import org.arjun.jwt.model.JWTToken;
import org.arjun.jwt.signer.RSA256Signer;
import org.arjun.jwt.signer.RSASigner;
import org.arjun.jwt.util.JWTUtils;
import org.arjun.jwt.validator.JWTValidator;
import org.arjun.jwt.validator.JWTValidatorImpl;
import org.arjun.jwt.validator.RSASignatureValidator;
import org.arjun.jwt.validator.SignatureValidator;

/**
 * Created by arjuns on 22/6/17.
 */
public class TestRSA {


    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {

        JOSEToken joseToken = new JOSEBuilder().
                addObjectType("JWT").
                addAlgorithm(JWSAlgorithm.RS256.name()).build();

        Claims claims = new ClaimsBuilder().
                addIssuer("arjun").
                addAudience("wm").
                addJwtID("ssss").
                addClaim("dummy", "aaa").build();

        JWTToken jwtToken = new JWTTokenBuilder().addJOSEToken(joseToken).addClaimsSet(claims).build();

        KeyPair keyPair = JWTUtils.generatePrivatePublicKeyPair("RSA", 2048);
        RSASigner signer = new RSA256Signer(keyPair);


        JWSSignedToken jwsSignedToken = new JWSSignedToken(jwtToken, signer);
        String encodedToken = jwsSignedToken.generateSignedToken();

        SignatureValidator signatureValidator = new RSASignatureValidator();
        JWTValidator jwtValidator = new JWTValidatorImpl(signatureValidator);
        try {
            System.out.println(jwtValidator.verify(encodedToken, signer));
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println(Base64.encodeBase64String(publicKey.getEncoded()));
        System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));


    }

}
