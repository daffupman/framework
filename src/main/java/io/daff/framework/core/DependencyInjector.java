package io.daff.framework.core;

import io.daff.framework.core.anno.Autowired;
import io.daff.framework.core.anno.Qualifier;
import io.daff.framework.utils.ClassUtil;
import io.daff.framework.utils.CollectionUtil;
import io.daff.framework.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * 依赖注入
 *
 * @author daff
 * @since 2021/8/22
 */
public class DependencyInjector {

    private final Logger logger = LoggerFactory.getLogger(DependencyInjector.class);
    private final BeanContainer beanContainer;

    public DependencyInjector(BeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void doIoc() {
        Set<Class<?>> classes = beanContainer.getClasses();
        if (CollectionUtil.isEmpty(classes)) {
            logger.warn("bean container has no class");
            return;
        }
        for (Class<?> clazz : classes) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> fieldType = field.getType();
                    String qualifierValue = getQualifierValue(field);
                    Object fieldValue = getFieldInstance(fieldType, qualifierValue);
                    if (fieldValue == null) {
                        throw new RuntimeException("bean container can not find autowired class: " + fieldType.getName());
                    }
                    Object targetBean = beanContainer.getBean(clazz);
                    ClassUtil.setField(field, targetBean, fieldValue);
                }
            }
        }
    }

    private String getQualifierValue(Field field) {
        Qualifier qualifier = field.getAnnotation(Qualifier.class);
        return qualifier == null ? null : qualifier.value();
    }

    private Object getFieldInstance(Class<?> fieldType, String qualifierClassName) {
        Object fieldValue = beanContainer.getBean(fieldType);
        if (fieldValue != null) {
            return fieldValue;
        }
        Class<?> implementClass = getImplementedClass(fieldType, qualifierClassName);
        if (implementClass == null) {
            return null;
        }
        return beanContainer.getBean(implementClass);
    }

    private Class<?> getImplementedClass(Class<?> clazz, String targetClassName) {

        Set<Class<?>> implementedClasses = beanContainer.getClassesBySuper(clazz);
        if (CollectionUtil.isEmpty(implementedClasses)) {
            return null;
        }

        if (StringUtil.isEmpty(targetClassName)) {
            if (implementedClasses.size() > 1) {
                throw new RuntimeException(clazz.getSimpleName() + " has multiple implemented Classes");
            }
            return implementedClasses.iterator().next();
        }

        for (Class<?> implementedClass : implementedClasses) {
            String simpleName = ClassUtil.getSimpleName(implementedClass);
            if (targetClassName.equalsIgnoreCase(simpleName)) {
                return implementedClass;
            }
        }

        return null;
    }
}
