package io.daff.framework.mvc.processor.impl;

import io.daff.framework.mvc.RequestProcessorExecutionChain;
import io.daff.framework.mvc.processor.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求的预处理器，包括编码以及路径处理
 *
 * @author daff
 * @since 2021/9/5
 */
public class PreRequestProcessor implements RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PreRequestProcessor.class);

    @Override
    public boolean process(RequestProcessorExecutionChain chain) throws Exception {

        // 设置请求编码
        chain.getRequest().setCharacterEncoding("UTF-8");

        // 去除路径末尾的 /
        String requestPath = chain.getRequestPath();
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            chain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
            logger.debug("remove the last /, now url path is {} ", chain.getRequestPath());
        }
        return true;
    }
}
