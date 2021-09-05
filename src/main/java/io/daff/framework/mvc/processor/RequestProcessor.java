package io.daff.framework.mvc.processor;

import io.daff.framework.mvc.RequestProcessorExecutionChain;

/**
 * 请求的处理器
 *
 * @author daff
 * @since 2021/9/5
 */
public interface RequestProcessor {

    boolean process(RequestProcessorExecutionChain requestProcessorExecutionChain) throws Exception;
}
