package org.arjun.jwt.validator;

import org.arjun.jwt.exception.InvalidTokenException;
import org.arjun.jwt.signer.Signer;

/**
 * Created by arjuns on 3/7/17.
 */
public interface JWTValidator {
    boolean verify(String encodedToken, Signer signer) throws InvalidTokenException;
}
