package io.daff.framework.aop;

import io.daff.framework.aop.anno.Aspect;
import io.daff.framework.aop.anno.Order;
import io.daff.framework.aop.aspect.AspectContext;
import io.daff.framework.aop.aspect.AspectProxy;
import io.daff.framework.aop.aspect.DefaultAspect;
import io.daff.framework.core.bean.BeanContainer;
import io.daff.framework.utils.CgProxyUtil;
import io.daff.framework.utils.CollectionUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author daff
 * @since 2021/8/28
 */
public class AspectWeaver {

    private final BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        // 获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);

        // 按要被注入的注解分组
        Map<Class<? extends Annotation>, List<AspectContext>> categorizedMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(aspectSet)) {
            for (Class<?> aspectClass : aspectSet) {
                if (verifyAspect(aspectClass)) {
                    throw new RuntimeException("Invalid Aspect Class: {" + aspectClass.getSimpleName() + "}. " +
                            "@Aspect and @Order have not been added to the Aspect Class, " +
                            "or Aspect class does not extend from DefaultAspect, or the value in Aspect Tag equals @Aspect.");
                }
                categorizeAspect(categorizedMap, aspectClass);
            }
        }

        // 织入代理逻辑，并替换原来的bean
        if (CollectionUtil.isNotEmpty(categorizedMap)) {
            categorizedMap.forEach(this::wave);
        }

    }

    private void wave(Class<? extends Annotation> annotation, List<AspectContext> aspects) {
        // 获取需要被代理的类集合
        Set<Class<?>> classesByAnnotation = beanContainer.getClassesByAnnotation(annotation);

        // 生成代理类
        if (CollectionUtil.isNotEmpty(classesByAnnotation)) {
            for (Class<?> originClass : classesByAnnotation) {
                AspectProxy aspectProxy = new AspectProxy(originClass, aspects);
                // 创建代理类
                Object proxy = CgProxyUtil.newProxy(originClass, aspectProxy);
                // 替换原来的类
                beanContainer.addBean(originClass, proxy);
            }
        }
    }

    /**
     * 将切面按Aspect注解的值分组
     */
    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectContext>> categorizedMap, Class<?> aspectClass) {
        Aspect aspectAnnotation = aspectClass.getAnnotation(Aspect.class);
        Order orderAnnotation = aspectClass.getAnnotation(Order.class);
        DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
        AspectContext aspectContext = new AspectContext(orderAnnotation.value(), defaultAspect);
        if (!categorizedMap.containsKey(aspectAnnotation.value())) {
            List<AspectContext> aspectContexts = new ArrayList<>();
            aspectContexts.add(aspectContext);
            categorizedMap.put(aspectAnnotation.value(), aspectContexts);
        } else {
            List<AspectContext> aspectContexts = categorizedMap.get(aspectAnnotation.value());
            aspectContexts.add(aspectContext);
        }
    }

    /**
     * 验证Aspect类的正确性
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return !aspectClass.isAnnotationPresent(Aspect.class)
                && !aspectClass.isAnnotationPresent(Order.class)
                && !DefaultAspect.class.isAssignableFrom(aspectClass)
                && aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
    }
}
