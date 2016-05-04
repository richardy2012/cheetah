package org.cheetah.commons.utils;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Max on 2016/5/4.
 */
public class Objects {
    private Objects() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }

    public static boolean equals(Object var0, Object var1) {
        return var0 == var1 || var0 != null && var0.equals(var1);
    }

    public static int hashCode(Object var0) {
        return var0 != null?var0.hashCode():0;
    }

    public static int hash(Object... var0) {
        return Arrays.hashCode(var0);
    }

    public static String toString(Object var0) {
        return String.valueOf(var0);
    }

    public static String toString(Object var0, String var1) {
        return var0 != null?var0.toString():var1;
    }

    public static <T> int compare(T var0, T var1, Comparator<? super T> var2) {
        return var0 == var1?0:var2.compare(var0, var1);
    }

    public static <T> T requireNonNull(T var0) {
        if(var0 == null) {
            throw new NullPointerException();
        } else {
            return var0;
        }
    }

    public static <T> T requireNonNull(T var0, String var1) {
        if(var0 == null) {
            throw new NullPointerException(var1);
        } else {
            return var0;
        }
    }

    public static boolean isNull(Object var0) {
        return var0 == null;
    }

    public static boolean nonNull(Object var0) {
        return var0 != null;
    }

}