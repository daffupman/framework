package io.daff.framework.mvc;

import io.daff.biz.BizEntrance;
import io.daff.framework.core.context.SimpleAnnotationConfigApplicationContext;
import io.daff.framework.mvc.processor.RequestProcessor;
import io.daff.framework.mvc.processor.impl.ControllerRequestProcessor;
import io.daff.framework.mvc.processor.impl.JspRequestProcessor;
import io.daff.framework.mvc.processor.impl.PreRequestProcessor;
import io.daff.framework.mvc.processor.impl.StaticResourceRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求的分发器。拦截所有请求，包括jsp，静态资源等，自主处理。
 *
 * @author daff
 * @since 2021/9/5
 */
@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<RequestProcessor> PROCESSORS = new ArrayList<>();

    @Override
    public void init() {
        // 初始化容器
        new SimpleAnnotationConfigApplicationContext(BizEntrance.class);

        // 初始化请求处理器责任链
        PROCESSORS.add(new PreRequestProcessor());
        PROCESSORS.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSORS.add(new JspRequestProcessor(getServletContext()));
        PROCESSORS.add(new ControllerRequestProcessor());

        logger.info("success");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 创建责任链对象实例
        RequestProcessorExecutionChain chain = new RequestProcessorExecutionChain(PROCESSORS, req, resp);
        // 通过责任链模式来依次调用请求处理器对请求进行处理
        chain.doRequestProcessor();
        // 对处理结果进行渲染
        chain.doRender();
    }
}
