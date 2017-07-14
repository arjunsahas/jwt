package org.arjun.jwt.validator;

import org.arjun.jwt.signer.Signer;

/**
 * Created by arjuns on 26/6/17.
 */
public interface SignatureValidator {

    boolean validate(String encodedToken, final Signer signer);
}
