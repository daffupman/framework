package io.daff.framework.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author daff
 * @since 2021/8/28
 */
public class CgProxyUtil {

    /**
     * 为目标类生成代理类
     *
     * @param target 目标类（无需实现某个类）
     * @param mi     目标类需要增加的代理方法
     * @return 目标类的代理类
     */
    public static Object newProxy(Class<?> target, MethodInterceptor mi) {
        return Enhancer.create(target, mi);
    }
}
