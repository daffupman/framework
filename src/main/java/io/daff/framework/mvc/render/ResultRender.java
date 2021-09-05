package io.daff.framework.mvc.render;

import io.daff.framework.mvc.RequestProcessorExecutionChain;

/**
 * 渲染请求结果
 *
 * @author daff
 * @since 2021/9/5
 */
public interface ResultRender {

    /**
     * 执行渲染
     */
    void render(RequestProcessorExecutionChain chain) throws Exception;
}
