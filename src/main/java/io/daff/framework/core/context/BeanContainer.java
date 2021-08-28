package io.daff.framework.core.context;

import io.daff.framework.core.anno.Component;
import io.daff.framework.core.anno.Controller;
import io.daff.framework.core.anno.Repository;
import io.daff.framework.core.anno.Service;
import io.daff.framework.utils.ClassUtil;
import io.daff.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean 容器
 *
 * @author daff
 * @since 2021/8/21
 */
public class BeanContainer {

    private final Logger logger = LoggerFactory.getLogger(BeanContainer.class);
    /**
     * 容器加载完成的标志位
     */
    private Boolean loaded = false;
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList(
            Component.class, Controller.class, Service.class, Repository.class
    );
    /**
     * class容器
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    private BeanContainer() {
    }

    /**
     * 获取容器实例
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 根据父类或接口获取子类或实现类
     */
    public Set<Class<?>> getClassesBySuper(Class<?> superClassOrInterface) {
        Set<Class<?>> classes = getClasses();
        if (CollectionUtil.isEmpty(classes)) {
            logger.warn("bean container has no class");
            return Collections.emptySet();
        }
        Set<Class<?>> implementedClasses = new HashSet<>();
        for (Class<?> clazz :classes){
            if (superClassOrInterface.isAssignableFrom(clazz) && !superClassOrInterface.equals(clazz)) {
                implementedClasses.add(clazz);
            }
        }
        return implementedClasses;
    }

    public Boolean isLoaded() {
        return loaded;
    }

    /**
     * 容器内的实例数量
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 将包中所有的class都加载到beanMap中
     */
    public synchronized void loadBeans(String packageName) {
        if (isLoaded()) {
            logger.warn("Bean Container has been loaded.");
            return;
        }
        Set<Class<?>> classes = ClassUtil.loadClasses(packageName);
        if (CollectionUtil.isEmpty(classes)) {
            logger.warn("no class in package: {}", packageName);
            return;
        }
        for (Class<?> clz : classes) {
            for (Class<? extends Annotation> beanAnnotation : BEAN_ANNOTATION) {
                if (clz.isAnnotationPresent(beanAnnotation)) {
                    beanMap.put(clz, ClassUtil.newInstance(clz));
                }
            }
        }
        loaded = true;
    }

    /**
     * 向容器中添加bean
     */
    public Object addBean(Class<?> clz, Object bean) {
        return beanMap.put(clz, bean);
    }

    /**
     * 移除容器中的bean
     */
    public Object removeBean(Class<?> clz) {
        return beanMap.remove(clz);
    }

    public Object getBean(Class<?> clz) {
        return beanMap.get(clz);
    }

    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 枚举可抵御反射和序列化攻击
     */
    private enum ContainerHolder {
        HOLDER;

        private final BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }
}
