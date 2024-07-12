package cn.gojava.app.common.utils.bean;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * bean工具类
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * Copy properties from source object to a new instance of target class.
     *
     * @param source the source object
     * @param target the target class
     * @param <T>    the type of the target class
     * @return a new instance of the target class with properties copied from source
     */
    public static <T, O> T copyProperties(O source, Class<T> target) {
        if (source == null) {
            return null;
        }
        try {
            T targetInstance = instantiateClass(target);
            copyProperties(source, targetInstance);
            return targetInstance;
        } catch (Exception e) {
            throw new RuntimeException("Could not copy properties from source to target", e);
        }
    }

    /**
     * Copy properties from a list of source objects to a list of new instances of target class.
     *
     * @param sourceList the list of source objects
     * @param target     the target class
     * @param <T>        the type of the target class
     * @return a list of new instances of the target class with properties copied from source objects
     */
    public static <T, O> List<T> copyProperties(List<O> sourceList, Class<T> target) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        List<T> targetList = new ArrayList<>();
        for (O source : sourceList) {
            targetList.add(copyProperties(source, target));
        }
        return targetList;
    }

}
