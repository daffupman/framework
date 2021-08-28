package io.daff.framework.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author daff
 * @since 2021/8/28
 */
public class TimeStatisticInvocationHandler implements InvocationHandler {

    private Object target;

    public TimeStatisticInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        System.out.println("小羊睡了" + (System.currentTimeMillis() - startTime) + "ms");
        return result;
    }
}
