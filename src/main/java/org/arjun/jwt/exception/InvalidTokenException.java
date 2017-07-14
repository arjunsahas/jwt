package org.arjun.jwt.exception;

/**
 * Created by arjuns on 3/7/17.
 */
public class InvalidTokenException extends Throwable {

    public InvalidTokenException(final String message) {
        super(message);
    }
}
