package io.daff.framework.core;

import org.junit.jupiter.api.*;

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
