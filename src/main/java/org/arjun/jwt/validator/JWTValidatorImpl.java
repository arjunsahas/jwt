package org.arjun.jwt.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.arjun.jwt.exception.InvalidTokenException;
import org.arjun.jwt.model.JWTToken;
import org.arjun.jwt.model.RegisteredClaimName;
import org.arjun.jwt.signer.Signer;
import org.arjun.jwt.util.JWTUtils;

/**
 * Created by arjuns on 3/7/17.
 */
public class JWTValidatorImpl implements JWTValidator {
    private SignatureValidator signatureValidator;

    public JWTValidatorImpl(final SignatureValidator signatureValidator) {
        this.signatureValidator = signatureValidator;
    }

    @Override
    public boolean verify(final String incomingToken, Signer signer) throws InvalidTokenException {
        JWTToken jwtToken = JWTUtils.convertStringToJWTToken(incomingToken);
        if (!(signatureValidator.validate(incomingToken, signer))) {
            throw new InvalidTokenException("Could not verify signature");
        }

        String date = getClaim(jwtToken, RegisteredClaimName.ISSUED_AT);
        if (date != null) {
            Date issuedAtDate = null;
            try {
                issuedAtDate = DateFormat.getInstance().parse(date);
            } catch (ParseException e) {
                throw new InvalidTokenException("IssueAt value invalid");
            }
            if (!verifyIssuedAt(issuedAtDate) || !verifyExpirationTime(issuedAtDate,
                    Integer.valueOf(getClaim(jwtToken, RegisteredClaimName
                            .EXPIRATION_TIME)))) {
                throw new InvalidTokenException("Could not verify the token");
            }
        }
        if (!verifyIssuer(getClaim(jwtToken, RegisteredClaimName.ISSUER))
                || !verifySubject(getClaim(jwtToken, RegisteredClaimName.SUBJECT))
                || !verifyAudience(getClaim(jwtToken, RegisteredClaimName.SUBJECT))
                || !verifyNotBefore()
                || !verifyJwtId(getClaim(jwtToken, RegisteredClaimName.JWT_ID))) {
            throw new RuntimeException("Could not verify the token");
        }
        return true;
    }

    protected boolean verifyJwtId(String jwtId) {
        return true;
    }

    protected boolean verifyIssuedAt(Date date) {
        return true;
    }

    protected boolean verifyNotBefore() {
        return true;
    }

    protected boolean verifyExpirationTime(Date issuedAt, int expirationTimeInSeconds) {
        return true;
    }

    protected boolean verifyAudience(String audience) {
        return true;
    }

    protected boolean verifySubject(String subject) {
        return true;
    }

    protected boolean verifyIssuer(String issuer) {
        return true;
    }

    private String getClaim(JWTToken jwtToken, RegisteredClaimName claimName) {
        return jwtToken.getClaimsSet().getClaims().get(claimName.getName());
    }

}
