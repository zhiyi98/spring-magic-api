package cn.gojava.app.common.utils.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlUtils {
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static <T> T toObject(String xml, Class<T> clz) {
        try {
            return xmlMapper.readValue(xml, clz);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T toObject(File file, Class<T> clz) {
        try {
            return xmlMapper.readValue(file, clz);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> List<T> toList(String xml, Class<T> clz) {
        try {
            return xmlMapper.readValue(xml, xmlMapper.getTypeFactory().constructCollectionType(List.class, clz));
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static JsonNode toJsonNode(String xml) {
        try {
            return xmlMapper.readTree(xml);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T nodeToValue(JsonNode node, Class<T> clz) {
        try {
            return xmlMapper.treeToValue(node, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String toXmlString(Object obj) {
        try {
            return xmlMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static JsonNode toJsonNode(File file) {
        try {
            return file != null ? xmlMapper.readTree(file) : null;
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }


}
