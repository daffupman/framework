package io.daff.framework.aop.anno;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面标记
 *
 * @author daff
 * @since 2021/8/22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 将切面逻辑织入到被该注解标记的类
       */
    Class<? extends Annotation> value();
}
