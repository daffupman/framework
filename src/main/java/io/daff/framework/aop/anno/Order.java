package io.daff.framework.aop.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定切面执行的顺序
 *
 * @author daff
 * @since 2021/8/22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    /**
     * 切面的执行优先级，值越小越优先
     */
    int value();
}
