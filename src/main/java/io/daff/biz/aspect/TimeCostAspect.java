package io.daff.biz.aspect;

import io.daff.framework.aop.anno.Aspect;
import io.daff.framework.aop.anno.Order;
import io.daff.framework.aop.aspect.DefaultAspect;
import io.daff.framework.core.anno.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author daff
 * @since 2021/8/28
 */
@Aspect(Controller.class)
@Order(1)
@SuppressWarnings("all")
public class TimeCostAspect extends DefaultAspect {

    private final Logger logger = LoggerFactory.getLogger(TimeCostAspect.class);
    private final ThreadLocal<Long> timeCostThreadLocal = new ThreadLocal<>();

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("开始执行：{}, {}", method.getName(), startTime);
        timeCostThreadLocal.set(startTime);
    }

    @Override
    public void after(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        Long startTime = timeCostThreadLocal.get();
        long endTime = System.currentTimeMillis();
        logger.info("执行结束：{}, {}，耗时：{}ms", method.getName(), endTime, endTime - startTime);
        timeCostThreadLocal.remove();
    }
}
