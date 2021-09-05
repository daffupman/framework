package io.daff.framework.mvc.model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Controller中定义的方法，每个方法对应一个请求
 *
 * @author daff
 * @since 2021/9/5
 */
public class ControllerMethod {

    private Class<?> controllerClass;
    private Method invokeMethod;
    private Map<String, Class<?>> methodParams;

    public ControllerMethod() {
    }

    public ControllerMethod(Class<?> controllerClass, Method invokeMethod, Map<String, Class<?>> methodParams) {
        this.controllerClass = controllerClass;
        this.invokeMethod = invokeMethod;
        this.methodParams = methodParams;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public Map<String, Class<?>> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Map<String, Class<?>> methodParams) {
        this.methodParams = methodParams;
    }
}
