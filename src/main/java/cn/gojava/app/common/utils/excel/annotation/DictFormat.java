package cn.gojava.app.common.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * 字典格式化
 * <p>实现将字典数据的值，格式化成字典数据的标签</p>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DictFormat {

    /**
     * @return 字典类型
     */
    String value();

}
