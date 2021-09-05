package io.daff.framework.core.context;

import io.daff.framework.aop.AspectWeaver;
import io.daff.framework.core.bean.BeanContainer;
import io.daff.framework.core.bean.DependencyInjector;

/**
 * 框架的入口。在构造方法中传入入口类，会读取入口类所在类的包以及子类中带有@Component、@Service、@Controller的类，
 * 并注入到bean容器中。使用getBean可以获取注入的bean。
 *
 * @author daff
 * @since 2021/8/22
 */
public class SimpleAnnotationConfigApplicationContext implements BeanFactory {

    private final BeanContainer beanContainer;

    public SimpleAnnotationConfigApplicationContext(Class<?> entranceClass) {
        String packageName = entranceClass.getPackageName();
        this.beanContainer = BeanContainer.getInstance();
        this.beanContainer.loadBeans(packageName);
        // aop
        new AspectWeaver().doAop();
        // ioc
        new DependencyInjector().doIoc();
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return beanContainer.getBean(clazz);
    }
}
