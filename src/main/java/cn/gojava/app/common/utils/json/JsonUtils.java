package cn.gojava.app.common.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * jackson 序列化对象工具
 */
public class JsonUtils {

    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(String str, Class<T> clz) {
        try {
            return objectMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T toObject(String str, TypeReference<T> clz) {
        try {
            return objectMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T toObject(File file, Class<T> clz) {
        try {
            return objectMapper.readValue(file, clz);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T toObject(File file, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(file, valueTypeRef);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> List<T> toList(String str, Class<T> clz) {
        try {
            return objectMapper.readValue(str, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static JsonNode toJsonNode(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T nodeToValue(JsonNode node, Class<T> clz) {
        try {
            return objectMapper.treeToValue(node, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String toJsonString(Object t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static JsonNode toJsonNode(File value) {
        try {
            return value != null ? objectMapper.readTree(value) : null;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}