package cn.gojava.app.common.utils;

import cn.gojava.app.common.exception.ServiceException;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public final class AssertUtils {

    /**
     * 校验到失败就结束
     */
    private static final Validator fastValidator;

    /**
     * 全部校验
     */
    private static final Validator allValidator;
    static {
        try (ValidatorFactory fastValidatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
             ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            fastValidator = fastValidatorFactory.getValidator();
            allValidator = validatorFactory.getValidator();
        }
    }

    /**
     * 注解验证参数(校验到失败就结束)
     * @param obj 校验对象
     */
    public static <T> void fastValidated(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = fastValidator.validate(obj);
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        String message = constraintViolations.iterator().next().getMessage();
        throw new ServiceException(message);
    }

    /**
     * 注解验证参数(全部校验，抛出异常)
     * @param obj 校验对象
     */
    public static <T> void allValidated(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = allValidator.validate(obj);
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        StringBuilder errorMessages = new StringBuilder();
        for (ConstraintViolation<T> violation : constraintViolations) {
            // 拼接异常信息
            errorMessages.append(violation.getMessage()).append(",");
        }
        // 去掉最后一个逗号
        String message = errorMessages.substring(0, errorMessages.length() - 1);
        throw new ServiceException(message);
    }

    /**
     * Assert a boolean expression, throwing <code>ServiceException</code>
     * if the test result is <code>false</code>.
     * <pre class="code">AssertUtils.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>ServiceException</code>
     * if the test result is <code>false</code>.
     * <pre class="code">AssertUtils.isTrue(i &gt; 0);</pre>
     * @param expression a boolean expression
     * @throws ServiceException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * Assert that an object is <code>null</code> .
     * <pre class="code">AssertUtils.isNull(value, "The value must be null");</pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that an object is <code>null</code> .
     * <pre class="code">AssertUtils.isNull(value);</pre>
     * @param object the object to check
     * @throws ServiceException if the object is not <code>null</code>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">AssertUtils.notNull(clazz, "The class must not be null");</pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">AssertUtils.notNull(clazz);</pre>
     * @param object the object to check
     * @throws ServiceException if the object is <code>null</code>
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be <code>null</code> and not the empty String.
     * <pre class="code">AssertUtils.hasLength(name, "Name must not be empty");</pre>
     * @param text the String to check
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be <code>null</code> and not the empty String.
     * <pre class="code">AssertUtils.hasLength(name);</pre>
     * @param text the String to check
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text) {
        hasLength(text,
                  "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * Assert that the given String has valid text content; that is, it must not
     * be <code>null</code> and must contain at least one non-whitespace character.
     * <pre class="code">AssertUtils.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasText
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that the given String has valid text content; that is, it must not
     * be <code>null</code> and must contain at least one non-whitespace character.
     * <pre class="code">AssertUtils.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @see StringUtils#hasText
     */
    public static void hasText(String text) {
        hasText(text,
                "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">AssertUtils.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param message the exception message to use if the assertion fails
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">AssertUtils.doesNotContain(name, "rod");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     */
    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring,
                       "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }


    /**
     * Assert that an array has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">AssertUtils.notEmpty(array, "The array must have elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if the object array is <code>null</code> or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (Objects.isNull(array)) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that an array has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">AssertUtils.notEmpty(array);</pre>
     * @param array the array to check
     * @throws ServiceException if the object array is <code>null</code> or has no elements
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(byte[] array, String msg) {
        if (Objects.isNull(array)) {
            throw new ServiceException(msg);
        }
    }

    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">AssertUtils.noNullElements(array, "The array must have non-null elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if the object array contains a <code>null</code> element
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object object : array) {
                if (object == null) {
                    throw new ServiceException(message);
                }
            }
        }
    }

    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">AssertUtils.noNullElements(array);</pre>
     * @param array the array to check
     * @throws ServiceException if the object array contains a <code>null</code> element
     */
    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">AssertUtils.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if the collection is <code>null</code> or has no elements
     */
    public static void notEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">AssertUtils.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @throws ServiceException if the collection is <code>null</code> or has no elements
     */
    public static void notEmpty(Collection collection) {
        notEmpty(collection,
                 "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a Map has entries; that is, it must not be <code>null</code>
     * and must have at least one entry.
     * <pre class="code">AssertUtils.notEmpty(map, "Map must have entries");</pre>
     * @param map the map to check
     * @param message the exception message to use if the assertion fails
     * @throws ServiceException if the map is <code>null</code> or has no entries
     */
    public static void notEmpty(Map map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ServiceException(message);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be <code>null</code>
     * and must have at least one entry.
     * <pre class="code">AssertUtils.notEmpty(map);</pre>
     * @param map the map to check
     * @throws ServiceException if the map is <code>null</code> or has no entries
     */
    public static void notEmpty(Map map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }


    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">AssertUtils.instanceOf(Foo.class, foo);</pre>
     * @param clazz the required class
     * @param obj the object to check
     * @throws ServiceException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">AssertUtils.instanceOf(Foo.class, foo);</pre>
     * @param type the type to check against
     * @param obj the object to check
     * @param message a message which will be prepended to the message produced by
     * the function itself, and which may be used to provide context. It should
     * normally end in a ": " or ". " so that the function generate message looks
     * ok when prepended to it.
     * @throws ServiceException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new ServiceException(message +
                                               "Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
                                               "] must be an instance of " + type);
        }
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
     * <pre class="code">AssertUtils.isAssignable(Number.class, myClass);</pre>
     * @param superType the super type to check
     * @param subType the sub type to check
     * @throws ServiceException if the classes are not assignable
     */
    public static void isAssignable(Class superType, Class subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
     * <pre class="code">AssertUtils.isAssignable(Number.class, myClass);</pre>
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param message a message which will be prepended to the message produced by
     * the function itself, and which may be used to provide context. It should
     * normally end in a ": " or ". " so that the function generate message looks
     * ok when prepended to it.
     * @throws ServiceException if the classes are not assignable
     */
    public static void isAssignable(Class superType, Class subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new ServiceException(message + subType + " is not assignable to " + superType);
        }
    }


    /**
     * Assert a boolean expression, throwing <code>IllegalStateException</code>
     * if the test result is <code>false</code>. Call isTrue if you wish to
     * throw ServiceException on an assertion failure.
     * <pre class="code">AssertUtils.state(id == null, "The id property must not already be initialized");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is <code>false</code>
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing {@link IllegalStateException}
     * if the test result is <code>false</code>.
     * <p>Call {@link #isTrue(boolean)} if you wish to
     * throw {@link ServiceException} on an assertion failure.
     * <pre class="code">AssertUtils.state(id == null);</pre>
     * @param expression a boolean expression
     * @throws IllegalStateException if the supplied expression is <code>false</code>
     */
    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

}
