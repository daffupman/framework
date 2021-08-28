package io.daff.framework.context;

/**
 *
 *
 * @author daff
 * @since 2021/8/22
 */
public interface BeanFactory {

    /**
     * 根据类型获取bean实例
     */
    Object getBean(Class<?> clazz);
}
