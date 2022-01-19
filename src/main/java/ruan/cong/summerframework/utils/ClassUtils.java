package ruan.cong.summerframework.utils;

import ruan.cong.summerframework.beans.factory.exception.ClassLoaderException;

public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            throw new ClassLoaderException("Cannot access thread context ClassLoader - falling back to system class loader...");
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }
}
