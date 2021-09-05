package io.daff.framework.mvc.processor.impl;

import io.daff.framework.core.bean.BeanContainer;
import io.daff.framework.mvc.RequestProcessorExecutionChain;
import io.daff.framework.mvc.anno.RequestMapping;
import io.daff.framework.mvc.anno.RequestParam;
import io.daff.framework.mvc.anno.ResponseBody;
import io.daff.framework.mvc.model.ControllerMethod;
import io.daff.framework.mvc.model.RequestPathInfo;
import io.daff.framework.mvc.processor.RequestProcessor;
import io.daff.framework.mvc.render.ResultRender;
import io.daff.framework.mvc.render.impl.JsonResultRender;
import io.daff.framework.mvc.render.impl.ResourceNotFoundResultRender;
import io.daff.framework.mvc.render.impl.ViewResultRender;
import io.daff.framework.utils.CollectionUtil;
import io.daff.framework.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller请求处理器
 *
 * @author daff
 * @since 2021/9/5
 */
public class ControllerRequestProcessor implements RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ControllerRequestProcessor.class);

    private final BeanContainer beanContainer;
    private final Map<RequestPathInfo, ControllerMethod> pathInfoControllerMethodMap = new ConcurrentHashMap<>();

    public ControllerRequestProcessor() {
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingClasses = beanContainer.getClassesByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingClasses);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingClasses) {
        if (CollectionUtil.isEmpty(requestMappingClasses)) {
            return;
        }

        for (Class<?> requestMappingClass : requestMappingClasses) {
            RequestMapping requestMapping = requestMappingClass.getAnnotation(RequestMapping.class);

            // 取一级路径
            String basePath = requestMapping.value();
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }

            // 取二级路径
            Method[] methods = requestMappingClass.getDeclaredMethods();
            if (methods.length <= 0) {
                continue;
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                    String methodPath = methodRequestMapping.value();
                    if (!methodPath.startsWith("/")) {
                        methodPath = "/" + methodPath;
                    }
                    // 完整的路径
                    String targetUrl = basePath + methodPath;

                    // 获取方法中的参数，以及参数的类型
                    Map<String, Class<?>> methodParams = new HashMap<>();
                    Parameter[] params = method.getParameters();
                    if (params.length > 0) {
                        for (Parameter param : params) {
                            RequestParam requestParam = param.getAnnotation(RequestParam.class);
                            if (requestParam != null) {
                                methodParams.put(requestParam.value(), param.getType());
                            }
                        }
                    }

                    // 将请求路径以及请求参数封装起来
                    String httpMethod = methodRequestMapping.method().toString();
                    RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, targetUrl);
                    if (this.pathInfoControllerMethodMap.containsKey(requestPathInfo)) {
                        throw new RuntimeException("duplicate url: {}, class: {}, method: {}, please check first");
                    }
                    ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, method, methodParams);
                    pathInfoControllerMethodMap.put(requestPathInfo, controllerMethod);
                }
            }
        }
    }

    @Override
    public boolean process(RequestProcessorExecutionChain chain) throws Exception {

        // 根据请求方法和路径获取对应的ControllerMethod实例
        String requestMethod = chain.getRequestMethod();
        String requestPath = chain.getRequestPath();
        ControllerMethod controllerMethod = pathInfoControllerMethodMap.get(new RequestPathInfo(requestMethod, requestPath));
        if (controllerMethod == null) {
            chain.setResultRender(new ResourceNotFoundResultRender());
        }

        Object result = invokeControllerMethod(controllerMethod, chain.getRequest());
        setResultRender(result, controllerMethod, chain);

        return true;
    }

    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorExecutionChain chain) {
        if (result == null) {
            return;
        }

        boolean returnJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        ResultRender resultRender = returnJson ? new JsonResultRender(result) : new ViewResultRender(result);
        chain.setResultRender(resultRender);
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {

        // 获取请求参数
        HashMap<String, String> requestParamMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> paramEntry : parameterMap.entrySet()) {
            String[] values = paramEntry.getValue();
            if (values != null && values.length > 0) {
                requestParamMap.put(paramEntry.getKey(), values[0]);
            }
        }

        // 根据获取到的请求参数名及其对应的值，以及controllerMethod里面的参数和类型的关系，去实例化出对应的参数
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParamMap = controllerMethod.getMethodParams();
        for (Map.Entry<String, Class<?>> methodParamEntry : methodParamMap.entrySet()) {
            String paramName = methodParamEntry.getKey();
            Class<?> paramType = methodParamEntry.getValue();
            String requestValue = requestParamMap.get(paramName);
            Object value = requestValue == null ? ConvertUtil.primitiveNull(paramType) : ConvertUtil.convert(paramType, requestValue);
            methodParams.add(value);
        }

        // 反射调用方法
        Object invokeClass = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result = null;
        try {
            if (methodParams.size() == 0) {
                    result = invokeMethod.invoke(invokeClass);
            } else {
                result = invokeMethod.invoke(invokeClass, methodParams.toArray());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
