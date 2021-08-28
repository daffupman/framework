package io.daff.framework.core;

import io.daff.biz.BizEntrance;
import io.daff.biz.controller.UserController;
import io.daff.framework.aop.AspectWeaver;
import io.daff.framework.core.bean.BeanContainer;
import io.daff.framework.core.bean.DependencyInjector;

/**
 * @author daff
 * @since 2021/8/28
 */
public class AspectWeaverTest {

    public static void main(String[] args) {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans(BizEntrance.class.getPackageName());
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();

        UserController userController = (UserController) beanContainer.getBean(UserController.class);
        userController.queryUser();
    }
}
