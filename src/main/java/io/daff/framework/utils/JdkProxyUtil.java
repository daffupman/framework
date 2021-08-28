package io.daff.framework.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author daff
 * @since 2021/8/28
 */
public class JdkProxyUtil {

    /**
     * 为目标类生成代理类
     *
     * @param target 被代理的类（该类必须实现某个接口）
     * @param handler 被代理类的InvocationHandler，其中包含了被代理的功能
     * @return 代理类，类型为target实现的接口中的其中一个，否则报错
     */
    @SuppressWarnings("unchecked")
    public static <T> T newProxy(T target, InvocationHandler handler) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
