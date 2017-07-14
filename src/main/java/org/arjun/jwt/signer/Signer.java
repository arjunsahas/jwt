package org.arjun.jwt.signer;

/**
 * Created by arjuns on 26/6/17.
 */
public interface Signer {

    String sign(String algorithm, String encodedToken);
}
