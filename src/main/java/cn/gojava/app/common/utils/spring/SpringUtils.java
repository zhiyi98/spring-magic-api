package cn.gojava.app.common.utils.spring;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
@Component
public final class SpringUtils extends SpringUtil {

    /**
     * 通过class获取Bean
     *
     * @param <T>   Bean类型
     * @param clazz Bean类
     * @param args  显式参数创建 Bean 实例时使用的参数
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz, Object... args) {
        return getBeanFactory().getBean(clazz, args);
    }

    /**
     * 通过name获取 Bean
     *
     * @param <T>  Bean类型
     * @param name Bean名称
     * @param args  显式参数创建 Bean 实例时使用的参数
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Object... args) {
        return (T) getBeanFactory().getBean(name, args);
    }

}
