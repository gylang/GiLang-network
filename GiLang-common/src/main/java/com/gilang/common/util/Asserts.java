//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gilang.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.exception.BaseCode;
import com.gilang.common.exception.ValidateException;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 拷贝值hutool  Assert
 * 修改异常抛出 改为业务校验异常
 */
public class Asserts {
    private static final CharSequence BETWEEN_TEMPLATE = "Length must be between {} and {}.";

    private Asserts() {
    }

    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> supplier) throws X {
        if (!expression) {
            throw (X) supplier.get();
        }
    }

    public static void isTrue(boolean expression, String errorMsgTemplate, Object... params) {
        isTrue(expression, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static <X extends Throwable> void isFalse(boolean expression, Supplier<X> errorSupplier) throws X {
        if (expression) {
            throw (X) errorSupplier.get();
        }
    }

    public static void isFalse(boolean expression, String errorMsgTemplate, Object... params) {
        isFalse(expression, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] - this expression must be false");
    }

    public static <X extends Throwable> void isNull(Object object, Supplier<X> errorSupplier) throws X {
        if (null != object) {
            throw (X) errorSupplier.get();
        }
    }

    public static void isNull(Object object, String errorMsgTemplate, Object... params) {
        isNull(object, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static <T, X extends Throwable> T notNull(T object, Supplier<X> errorSupplier) throws X {
        if (null == object) {
            throw (X) errorSupplier.get();
        } else {
            return object;
        }
    }

    public static <T> T notNull(T object, BaseCode baseCode) {
        return notNull(object, () -> new ValidateException(baseCode));
    }

    public static <T> T notNull(T object, String errorMsgTemplate, Object... params) {
        return notNull(object, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <T> T notNull(T object) {
        return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static <T extends CharSequence, X extends Throwable> T notEmpty(T text, Supplier<X> errorSupplier) throws X {
        if (StrUtil.isEmpty(text)) {
            throw (X) errorSupplier.get();
        } else {
            return text;
        }
    }

    public static <T extends CharSequence> T notEmpty(T text, String errorMsgTemplate, Object... params) {
        return notEmpty(text, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <T extends CharSequence> T notEmpty(T text) {
        return notEmpty(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static <T extends CharSequence, X extends Throwable> T notBlank(T text, Supplier<X> errorMsgSupplier) throws X {
        if (StrUtil.isBlank(text)) {
            throw (X) errorMsgSupplier.get();
        } else {
            return text;
        }
    }

    public static <T extends CharSequence> T notBlank(T text, String errorMsgTemplate, Object... params) {
        return notBlank(text, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <T extends CharSequence> T notBlank(T text) {
        return notBlank(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    public static <T extends CharSequence, X extends Throwable> T notContain(CharSequence textToSearch, T substring, Supplier<X> errorSupplier) throws X {
        if (StrUtil.contains(textToSearch, substring)) {
            throw (X) errorSupplier.get();
        } else {
            return substring;
        }
    }

    public static String notContain(String textToSearch, String substring, String errorMsgTemplate, Object... params) {
        return notContain(textToSearch, substring, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static String notContain(String textToSearch, String substring) {
        return notContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [{}]", substring);
    }

    public static <T, X extends Throwable> T[] notEmpty(T[] array, Supplier<X> errorSupplier) throws X {
        if (ArrayUtil.isEmpty(array)) {
            throw (X) errorSupplier.get();
        } else {
            return array;
        }
    }

    public static <T> T[] notEmpty(T[] array, String errorMsgTemplate, Object... params) {
        return notEmpty(array, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <T> T[] notEmpty(T[] array) {
        return notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static <T, X extends Throwable> T[] noNullElements(T[] array, Supplier<X> errorSupplier) throws X {
        if (ArrayUtil.hasNull(array)) {
            throw (X) errorSupplier.get();
        } else {
            return array;
        }
    }

    public static <T> T[] noNullElements(T[] array, String errorMsgTemplate, Object... params) {
        return noNullElements(array, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <T> T[] noNullElements(T[] array) {
        return noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    public static <E, T extends Iterable<E>, X extends Throwable> T notEmpty(T collection, Supplier<X> errorSupplier) throws X {
        if (CollUtil.isEmpty(collection)) {
            throw (X) errorSupplier.get();
        } else {
            return collection;
        }
    }

    public static <E, T extends Iterable<E>> T notEmpty(T collection, String errorMsgTemplate, Object... params) {
        return notEmpty(collection, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <E, T extends Iterable<E>> T notEmpty(T collection) {
        return notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    public static <K, V, T extends Map<K, V>, X extends Throwable> T notEmpty(T map, Supplier<X> errorSupplier) throws X {
        if (MapUtil.isEmpty(map)) {
            throw (X) errorSupplier.get();
        } else {
            return map;
        }
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(T map, String errorMsgTemplate, Object... params) {
        return notEmpty(map, () -> new ValidateException(StrUtil.format(errorMsgTemplate, params)));
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(T map) {
        return notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    public static <T> T isInstanceOf(Class<?> type, T obj) {
        return isInstanceOf(type, obj, "Object [{}] is not instanceof [{}]", obj, type);
    }

    public static <T> T isInstanceOf(Class<?> type, T obj, String errorMsgTemplate, Object... params) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new ValidateException(StrUtil.format(errorMsgTemplate, params));
        } else {
            return obj;
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "{} is not assignable to {})", subType, superType);
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String errorMsgTemplate, Object... params) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new ValidateException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void state(boolean expression, Supplier<String> errorMsgSupplier) {
        if (!expression) {
            throw new ValidateException(errorMsgSupplier.get());
        }
    }

    public static void state(boolean expression, String errorMsgTemplate, Object... params) {
        if (!expression) {
            throw new ValidateException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    public static int checkIndex(int index, int size) {
        return checkIndex(index, size, "[Assertion failed]");
    }

    public static int checkIndex(int index, int size, String errorMsgTemplate, Object... params) {
        if (index >= 0 && index < size) {
            return index;
        } else {
            throw new ValidateException(badIndexMsg(index, size, errorMsgTemplate, params));
        }
    }

    public static int checkBetween(int value, int min, int max) {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw new ValidateException(StrUtil.format(BETWEEN_TEMPLATE, min, max));
        }
    }

    public static long checkBetween(long value, long min, long max) {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw new ValidateException(StrUtil.format(BETWEEN_TEMPLATE, min, max));
        }
    }

    public static double checkBetween(double value, double min, double max) {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw new ValidateException(StrUtil.format(BETWEEN_TEMPLATE, min, max));
        }
    }

    public static Number checkBetween(Number value, Number min, Number max) {
        notNull(value);
        notNull(min);
        notNull(max);
        double valueDouble = value.doubleValue();
        double minDouble = min.doubleValue();
        double maxDouble = max.doubleValue();
        if (valueDouble >= minDouble && valueDouble <= maxDouble) {
            return value;
        } else {
            throw new ValidateException(StrUtil.format(BETWEEN_TEMPLATE, min, max));
        }
    }

    private static String badIndexMsg(int index, int size, String desc, Object... params) {
        if (index < 0) {
            return StrUtil.format("{} ({}) must not be negative", StrUtil.format(desc, params), index);
        } else if (size < 0) {
            throw new ValidateException("negative size: " + size);
        } else {
            return StrUtil.format("{} ({}) must be less than size ({})", StrUtil.format(desc, params), index, size);
        }
    }
}
