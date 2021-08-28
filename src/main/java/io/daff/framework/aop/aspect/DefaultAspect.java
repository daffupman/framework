package io.daff.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author daff
 * @since 2021/8/28
 */
@SuppressWarnings("all")
public abstract class DefaultAspect {

    /**
     * 前置通知
     *
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     * @throws Throwable 异常
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
    }

    /**
     * 执行成功通知
     *
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     * @param returnValue 执行结果
     * @return 执行结果
     * @throws Throwable 异常
     */
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }

    /**
     * 执行成功通知
     *
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     * @param e 异常
     */
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
    }

    /**
     * 执行成功通知
     *
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     */
    public void after(Class<?> targetClass, Method method, Object[] args) throws Throwable {
    }
}
