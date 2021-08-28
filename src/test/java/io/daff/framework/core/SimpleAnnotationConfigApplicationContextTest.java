package io.daff.framework.core;

import io.daff.biz.BizEntrance;
import io.daff.biz.controller.UserController;
import io.daff.framework.core.context.SimpleAnnotationConfigApplicationContext;
import org.junit.jupiter.api.Test;

/**
 * @author daff
 * @since 2021/8/22
 */
public class SimpleAnnotationConfigApplicationContextTest {

    private final SimpleAnnotationConfigApplicationContext simpleAnnotationConfigApplicationContext;

    {
        this.simpleAnnotationConfigApplicationContext = new SimpleAnnotationConfigApplicationContext(BizEntrance.class);
    }

    @Test
    public void testGetBean() {
        UserController userController = (UserController) simpleAnnotationConfigApplicationContext.getBean(UserController.class);
        userController.queryUser();
    }
}
