package org.arjun.jwt.signer;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by arjuns on 26/6/17.
 */
public class HMACSigner implements Signer {

    private String secretKey;

    public HMACSigner(final String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String sign(String algorithm, String encodedToken) {
        Mac cryptographicAlgorithm = null;
        try {
            cryptographicAlgorithm = getCryptographicAlgorithm(algorithm, secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] bytes = cryptographicAlgorithm.doFinal(encodedToken.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(bytes);
    }

    private static Mac getCryptographicAlgorithm(
            String algorithm, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(secretKey.getBytes(), algorithm);
        mac.init(secret_key);
        return mac;
    }

}
