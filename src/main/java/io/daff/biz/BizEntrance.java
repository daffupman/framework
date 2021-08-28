package io.daff.biz;

import io.daff.biz.controller.UserController;
import io.daff.framework.context.SimpleAnnotationConfigApplicationContext;

/**
 * @author daff
 * @since 2021/8/22
 */
public class BizEntrance {

    public static void main(String[] args) {
        SimpleAnnotationConfigApplicationContext simpleAnnotationConfigApplicationContext = new SimpleAnnotationConfigApplicationContext(BizEntrance.class);
        UserController userController = (UserController) simpleAnnotationConfigApplicationContext.getBean(UserController.class);
        userController.queryUser();
    }
}
