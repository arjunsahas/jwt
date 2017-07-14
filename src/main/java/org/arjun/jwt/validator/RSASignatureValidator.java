package org.arjun.jwt.validator;

import org.apache.commons.lang3.StringUtils;
import org.arjun.jwt.model.JOSEHeader;
import org.arjun.jwt.model.JOSEToken;
import org.arjun.jwt.JWSAlgorithm;
import org.arjun.jwt.model.JWTToken;
import org.arjun.jwt.util.JWTUtils;
import org.arjun.jwt.signer.RSASigner;
import org.arjun.jwt.signer.Signer;

/**
 * Created by arjuns on 26/6/17.
 */
public class RSASignatureValidator implements SignatureValidator {

    @Override
    public boolean validate(final String encodedToken, final Signer signer) {
        JWTToken jwtToken = JWTUtils.convertStringToJWTToken(encodedToken);
        JOSEToken joseToken = jwtToken.getJOSEToken();
        String[] split = StringUtils.split(encodedToken, ".");
        StringBuilder builder = new StringBuilder();
        builder.append(split[0]).append(".").append(split[1]);
        RSASigner rsaSigner = (RSASigner) signer;
        return rsaSigner
                .verify(JWSAlgorithm.valueOf(joseToken.getTokens().get(JOSEHeader.ALGORITHM.getName())).getAlgorithm(),
                        split[2]);
    }
}
