package io.daff.framework.core;

/**
 * @author daff
 * @since 2021/8/22
 */
public class SimpleApplicationContext implements BeanFactory{

    private final BeanContainer beanContainer;

    public SimpleApplicationContext(String scanPath) {
        this.beanContainer = BeanContainer.getInstance();
        this.beanContainer.loadBeans(scanPath);
        DependencyInjector dependencyInjector = new DependencyInjector(this.beanContainer);
        dependencyInjector.doIoc();
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return beanContainer.getBean(clazz);
    }
}
