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
@Order(2)
@SuppressWarnings("all")
public class GhostAspect extends DefaultAspect {

    protected Logger logger = LoggerFactory.getLogger(GhostAspect.class);

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        logger.info("呼呼");
    }

    @Override
    public void after(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        logger.info("哗哗");
    }
}
