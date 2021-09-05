package io.daff.framework.mvc.processor.impl;

import io.daff.framework.mvc.RequestProcessorExecutionChain;
import io.daff.framework.mvc.processor.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * 静态资源请求的处理，包括图片，css和js文件等。
 *
 * @author daff
 * @since 2021/9/5
 */
public class StaticResourceRequestProcessor implements RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceRequestProcessor.class);

    private static final String TOMCAT_DEFAULT_SERVLET = "default";
    private static final String STATIC_RESOURCE_PREFIX = "/static/";
    // tomcat 默认的请求派发器
    private final RequestDispatcher defaultDispatcher;

    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(TOMCAT_DEFAULT_SERVLET);
        if (this.defaultDispatcher == null) {
            throw new RuntimeException("no default tomcat servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorExecutionChain chain) throws Exception {
        if (isStaticResource(chain.getRequestPath())) {
            defaultDispatcher.forward(chain.getRequest(), chain.getResponse());
            return false;
        }
        return false;
    }

    /**
     * 根据请求路径判断是否该请求是否是静态资源
     */
    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
