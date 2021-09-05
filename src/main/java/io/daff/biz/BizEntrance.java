package io.daff.biz;

import io.daff.biz.controller.UserController;
import io.daff.framework.core.context.BeanFactory;
import io.daff.framework.core.context.SimpleAnnotationConfigApplicationContext;

/**
 * @author daff
 * @since 2021/8/22
 */
public class BizEntrance {

    public static void main(String[] args) {
        BeanFactory beanFactory = new SimpleAnnotationConfigApplicationContext(BizEntrance.class);
        UserController userController = (UserController) beanFactory.getBean(UserController.class);
        userController.queryUser();
    }
}
