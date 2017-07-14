package org.arjun.jwt.signer;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by arjuns on 26/6/17.
 */
public class RSA256Encrypter {

    private final KeyPair keyPair;

    public RSA256Encrypter(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String sign(String algorithm, String encodedToken) {
        byte[] uuidEncryptedWithPrivateKey = null;
        try {
            Cipher cryptographicAlgorithm = getCryptographicAlgorithm(algorithm, Cipher.ENCRYPT_MODE,
                    keyPair.getPublic());
            uuidEncryptedWithPrivateKey = cryptographicAlgorithm.doFinal(encodedToken.getBytes());
        } catch (BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(uuidEncryptedWithPrivateKey);
    }

    public String verify(final String algorithm, String encryptedJwtToken) {
        String decodedToken = null;
        try {
            Cipher cryptographicAlgorithm = getCryptographicAlgorithm(algorithm, Cipher.DECRYPT_MODE,
                    keyPair.getPrivate());
            byte[] decryptedJWTToken = cryptographicAlgorithm.doFinal(Base64.decodeBase64(encryptedJwtToken));
            decodedToken = new String(decryptedJWTToken, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        System.out.println(decodedToken);
        return decodedToken;
    }

    private static Cipher getCryptographicAlgorithm(String algorithm, int mode, Key key) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(mode, key);
        return cipher;
    }
}
