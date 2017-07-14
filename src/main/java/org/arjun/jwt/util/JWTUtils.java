package org.arjun.jwt.util;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.arjun.jwt.model.Claims;
import org.arjun.jwt.model.JOSEToken;
import org.arjun.jwt.model.JWTToken;
import org.arjun.jwt.util.JsonUtils;

/**
 * Created by arjuns on 23/6/17.
 */
public class JWTUtils {

    public static JWTToken convertStringToJWTToken(String encodedToken) {
        String[] split = StringUtils.split(encodedToken, ".");
        JWTToken jwtToken = new JWTToken();
        for (int i = 0; i < split.length; i++) {
            byte[] bytes = Base64.decodeBase64(split[i]);
            try {
                if (i == 0) {
                    Map<String, String> map = JsonUtils.toObject(bytes, LinkedHashMap.class);
                    JOSEToken joseToken = new JOSEToken();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        joseToken.addToken(entry.getKey(), entry.getValue());
                    }
                    jwtToken.setJOSEToken(joseToken);
                } else if (i == 1) {
                    Map<String, String> map = JsonUtils.toObject(bytes, Map.class);
                    Claims claims = new Claims();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        claims.addClaim(entry.getKey(), entry.getValue());
                    }
                    jwtToken.setClaimsSet(claims);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jwtToken;
    }

    public static KeyPair generatePrivatePublicKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(keySize);
        return kpg.generateKeyPair();
    }

}
