package io.daff.framework.core;

import io.daff.biz.controller.UserController;
import org.junit.jupiter.api.Test;

/**
 * @author daff
 * @since 2021/8/22
 */
public class SimpleApplicationContextTest {

    private final SimpleApplicationContext simpleApplicationContext;

    {
        this.simpleApplicationContext = new SimpleApplicationContext("io.daff.biz");
    }

    @Test
    public void testGetBean() {
        UserController userController = (UserController) simpleApplicationContext.getBean(UserController.class);
        userController.queryUser();
    }
}
