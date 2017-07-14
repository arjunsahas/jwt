package org.arjun.jwt.builder;

import org.apache.commons.lang3.StringUtils;
import org.arjun.jwt.model.JOSEHeader;
import org.arjun.jwt.model.JOSEToken;

/**
 * Created by arjuns on 22/6/17.
 */

public class JOSEBuilder {
    private String algorithm;
    private String objectType;
    private String contentType;

    public JOSEBuilder addAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public JOSEBuilder addObjectType(String algorithmType) {
        this.objectType = algorithmType;
        return this;
    }

    public JOSEBuilder addContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public JOSEToken build() {
        JOSEToken joseToken = new JOSEToken();
        if (StringUtils.isNotBlank(objectType)) {
            joseToken.addToken(JOSEHeader.OBJECT_TYPE.getName(), objectType);
        }
        if (StringUtils.isNotBlank(algorithm)) {
            joseToken.addToken(JOSEHeader.ALGORITHM.getName(), algorithm);
        }
        if (StringUtils.isNotBlank(contentType)) {
            joseToken.addToken(JOSEHeader.CONTENT_TYPE.getName(), contentType);
        }
        return joseToken;
    }
}
