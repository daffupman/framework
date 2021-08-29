package io.daff.framework.aop.aspect;

import io.daff.framework.utils.CollectionUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

/**
 * @author daff
 * @since 2021/8/28
 */
public class AspectProxy implements MethodInterceptor {

    /**
     * 被代理的类
     */
    private final Class<?> target;
    /**
     * 被代理的类的通知
     */
    private final List<AspectContext> aspectContexts;

    public AspectProxy(Class<?> target, List<AspectContext> aspectContexts) {
        this.target = target;
        this.aspectContexts = sortAspectContextsByOrder(aspectContexts);
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        if (CollectionUtil.isEmpty(aspectContexts)) {
            return null;
        }

        Object returnValue;

        try {
            // 按order升序执行前置通知
            invokeBeforeAdvices(method, args);
            // 执行被代理的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            // 执行成功：按order降序执行后置成功通知
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Throwable e) {
            // 执行已成：按order降序执行后置异常通知
            invokeAfterThrowingAdvices(method, args, e);
            throw e;
        } finally {
            // 按order降序执行后置通知
            invokeAfterAdvices(method, args);
        }

        return returnValue;
    }

    private void invokeAfterAdvices(Method method, Object[] args) throws Throwable {
        for (int i = aspectContexts.size() - 1; i >= 0; i--) {
            aspectContexts.get(i).getAspectObject().after(target, method, args);
        }
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Throwable e) throws Throwable {
        for (int i = aspectContexts.size() - 1; i >= 0; i--) {
            aspectContexts.get(i).getAspectObject().afterThrowing(target, method, args, e);
        }
    }

    /**
     * 按order降序执行后置成功通知
     */
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for (int i = aspectContexts.size() - 1; i >= 0; i--) {
            result = aspectContexts.get(i).getAspectObject()
                    .afterReturning(target, method, args, returnValue);
        }
        return result;
    }

    /**
     * 按order升序执行前置通知
     */
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectContext aspectContext : this.aspectContexts) {
            aspectContext.getAspectObject().before(target, method, args);
        }
    }

    /**
     * 按order从小到大排序
     */
    private List<AspectContext> sortAspectContextsByOrder(List<AspectContext> aspectContexts) {
        aspectContexts.sort(Comparator.comparing(AspectContext::getOrderIndex));
        return aspectContexts;
    }
}
