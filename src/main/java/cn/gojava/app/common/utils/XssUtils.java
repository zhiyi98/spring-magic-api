package cn.gojava.app.common.utils;

import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * XSS过滤，解决奇安信漏洞扫描
 */
@Slf4j
public class XssUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 过滤XSS漏洞
     */
    public static <T> Object filterData(T data) {
        if (ObjectUtils.isEmpty(data)) {
            return data;
        }
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            String unescaped = HtmlUtil.unescape(HtmlUtil.filter(jsonData));
            return objectMapper.readValue(unescaped, TypeFactory.rawClass(data.getClass()));
        } catch (Exception e) {
            log.error("filterData error: {}", e.getMessage(), e);
            return null;
        }
    }

}
