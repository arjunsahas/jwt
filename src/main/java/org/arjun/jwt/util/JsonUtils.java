package org.arjun.jwt.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by arjuns on 22/6/17.
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static String toJsonString(Map map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    public static <T> T toObject(byte[] bytes, Class<T> clazz) throws IOException {
        return objectMapper.readValue(bytes, clazz);
    }
}