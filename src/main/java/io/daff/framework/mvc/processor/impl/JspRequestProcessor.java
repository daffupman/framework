package io.daff.framework.mvc.processor.impl;

import io.daff.framework.mvc.RequestProcessorExecutionChain;
import io.daff.framework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * jsp资源请求的处理
 *
 * @author daff
 * @since 2021/9/5
 */
public class JspRequestProcessor implements RequestProcessor {

    /**
     * jsp请求的RequestDispatcher的名称
     */
    private static final String JSP_SERVLET = "jsp";
    /**
     * jsp请求资源路径前缀
     */
    private static final String JSP_RESOURCE_PREFIX = "/templates/";
    private final RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext) {
        this.jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (this.jspServlet == null) {
            throw new RuntimeException("no jsp servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorExecutionChain chain) throws Exception {
        if (jsJspResource(chain.getRequestPath())) {
            jspServlet.forward(chain.getRequest(), chain.getResponse());
            return false;
        }
        return true;
    }

    private boolean jsJspResource(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_PREFIX);
    }
}
