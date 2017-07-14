package org.arjun.jwt.signer;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by arjuns on 26/6/17.
 */
public class RSA256Signer implements RSASigner {

    private final KeyPair keyPair;

    public RSA256Signer(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public String sign(String algorithm, String encodedToken) {
        byte[] uuidEncryptedWithPrivateKey = null;
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(keyPair.getPrivate());
            uuidEncryptedWithPrivateKey = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(uuidEncryptedWithPrivateKey);
    }

    @Override
    public boolean verify(final String algorithm, String encryptedJwtToken) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(keyPair.getPublic());
            return signature.verify(Base64.decodeBase64(encryptedJwtToken.getBytes()));
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

}
