package io.daff.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author daff
 * @since 2021/8/21
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    private static final String FILE_PROTOCOL = "file";

    /**
     * 对指定包提取Class类
     */
    public static Set<Class<?>> loadClasses(String packageName) {
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            logger.warn("unable to retrieve anything from package: {}", packageName);
            return null;
        }
        Set<Class<?>> classes = null;
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            // 处理文件类型的资源
            classes = new HashSet<>();
            File packageDir = new File(url.getPath());
            extractClassFile(classes, packageDir, packageName);
        }
        // TODO 对其他类资源的处理
        return classes;
    }

    /**
     * 将指定包下的所有class文件都实例化放入class集合中
     */
    private static void extractClassFile(Set<Class<?>> classes, File packageFile, String entrancePackageName) {
        if (!packageFile.isDirectory()) {
            return;
        }
        File[] subFiles = packageFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String absolutePath = file.getAbsolutePath();
                if (absolutePath.endsWith(".class")) {
                    // 对于class文件直接加载
                    addToClasses(absolutePath);
                }
                return false;
            }

            private void addToClasses(String absolutePath) {
                absolutePath = absolutePath.replace(File.separator, ".");
                String classFullName = absolutePath.substring(absolutePath.indexOf(entrancePackageName), absolutePath.lastIndexOf("."));
                Class<?> resolvedClass = loadClass(classFullName);
                classes.add(resolvedClass);
            }
        });

        if (subFiles != null) {
            for (File subFile : subFiles) {
                extractClassFile(classes, subFile, entrancePackageName);
            }
        }
    }

    public static Class<?> loadClass(String classFullName) {
        try {
            return Class.forName(classFullName);
        } catch (ClassNotFoundException e) {
            logger.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 实例化class
     */
    @SuppressWarnings("all")
    public static <T> T newInstance(Class<?> clz) {
        try {
            Constructor<?> con = clz.getDeclaredConstructor();
            con.setAccessible(true);
            return (T) con.newInstance();
        } catch (Exception e) {
            logger.error("instance class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 给对象的属性设置值
     */
    public static void setField(Field field, Object obj, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("set field to obj error", e);
            throw new RuntimeException(e);
        }
    }

    public static String getSimpleName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        String firstLetterLowerCase = simpleName.substring(0, 1).toLowerCase();
        return firstLetterLowerCase + simpleName.substring(1);
    }
}
