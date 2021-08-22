package io.daff.framework.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author daff
 * @since 2021/8/22
 */
public class DependencyInjectorTest {

    @DisplayName("测试依赖注入")
    @Test
    public void testDoIoc() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("io.daff.biz");
        DependencyInjector di = new DependencyInjector(beanContainer);
        di.doIoc();
    }
}
