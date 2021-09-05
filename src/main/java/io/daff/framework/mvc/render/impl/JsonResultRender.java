package io.daff.framework.mvc.render.impl;

import io.daff.framework.mvc.RequestProcessorExecutionChain;
import io.daff.framework.mvc.render.ResultRender;
import io.daff.framework.utils.JacksonUtil;

import java.io.PrintWriter;

/**
 * Json响应渲染器
 *
 * @author daff
 * @since 2021/9/5
 */
public class JsonResultRender implements ResultRender {

    private final Object data;

    public JsonResultRender(Object data) {
        this.data = data;
    }

    @Override
    public void render(RequestProcessorExecutionChain chain) throws Exception {
        chain.getResponse().setContentType("application/json");
        chain.getResponse().setCharacterEncoding("UTF-8");
        try (PrintWriter writer = chain.getResponse().getWriter()) {
            String json = JacksonUtil.beanToString(this.data);
            writer.write(json);
            writer.flush();
        }
    }
}
