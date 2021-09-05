package io.daff.framework.mvc;

import io.daff.framework.mvc.processor.RequestProcessor;
import io.daff.framework.mvc.render.ResultRender;
import io.daff.framework.mvc.render.impl.DefaultResultRender;
import io.daff.framework.mvc.render.impl.InternalErrorResultRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求处理器执行链
 *
 * @author daff
 * @since 2021/9/5
 */
public class RequestProcessorExecutionChain {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessorExecutionChain.class);

    // 处理器集合
    private final List<RequestProcessor> requestProcessors;
    // 请求
    private final HttpServletRequest request;
    // 响应
    private final HttpServletResponse response;
    // 请求方法
    private String requestMethod;
    // 请求路径
    private String requestPath;
    // 响应码
    private Integer responseCode;
    // 请求结果渲染器
    private ResultRender resultRender;

    public RequestProcessorExecutionChain(List<RequestProcessor> requestProcessors, HttpServletRequest request, HttpServletResponse response) {
        this.requestProcessors = requestProcessors;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    public void doRequestProcessor() {

        try {
            for (RequestProcessor requestProcessor : this.requestProcessors) {
                if (!requestProcessor.process(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            this.resultRender = new InternalErrorResultRender();
            logger.error("process request error", e);
        }
    }

    public void doRender() {

        if (this.resultRender == null) {
            this.resultRender = new DefaultResultRender();
        }

        try {
            this.resultRender.render(this);
        } catch (Exception e) {
            logger.error("render error", e);
            throw new RuntimeException(e);
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void setResultRender(ResultRender resultRender) {
        this.resultRender = resultRender;
    }
}
