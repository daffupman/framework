package io.daff.framework.mvc.anno;

import io.daff.framework.mvc.constants.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author daff
 * @since 2021/8/29
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value();
    RequestMethod method() default RequestMethod.GET;
}
