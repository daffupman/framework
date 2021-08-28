package io.daff.framework.core;

import io.daff.framework.core.context.BeanContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author daff
 * @since 2021/8/22
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {

    private static BeanContainer beanContainer;

    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }

    @DisplayName("测试beanContainer的bean加载功能")
    @Test
    @Order(1)
    public void testLoadBeans() {
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("io.daff.biz");
        Assertions.assertEquals(4, beanContainer.size());
    }
}
