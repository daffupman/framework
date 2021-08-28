package io.daff.framework.core.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author daff
 * @since 2021/8/28
 */
public class SentinelMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("睡着了");
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("睡醒了");
        return result;
    }
}
