package org.arjun.jwt.model;

/**
 *
 * JSON Object Signing and Encryption Header.
 *
 * Created by arjuns on 22/6/17.
 */
public enum JOSEHeader {
    ALGORITHM("alg"),
    OBJECT_TYPE("typ"),
    CONTENT_TYPE("cty");

    private String name;

    JOSEHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
