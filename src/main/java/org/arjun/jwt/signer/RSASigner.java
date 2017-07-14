package org.arjun.jwt.signer;

/**
 * Created by arjuns on 26/6/17.
 */
public interface RSASigner extends Signer {

    boolean verify(String algorithm, String encodedToken);
}
