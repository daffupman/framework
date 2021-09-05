package io.daff.framework.mvc.render.impl;

import io.daff.framework.mvc.RequestProcessorExecutionChain;
import io.daff.framework.mvc.model.ModelAndView;
import io.daff.framework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 页面渲染器
 *
 * @author daff
 * @since 2021/9/5
 */
public class ViewResultRender implements ResultRender {

    private static final String VIEW_PATH = "/templates/";
    private final ModelAndView modelAndView;

    public ViewResultRender(Object result) {
        if (result instanceof ModelAndView) {
            this.modelAndView = ((ModelAndView) result);
        } else if (result instanceof String) {
            this.modelAndView = new ModelAndView().setView(((String) result));
        } else {
            throw new RuntimeException("illegal request result type");
        }
    }

    @Override
    public void render(RequestProcessorExecutionChain chain) throws Exception {
        HttpServletRequest request = chain.getRequest();
        HttpServletResponse response = chain.getResponse();
        String viewPath = modelAndView.getViewPath();
        Map<String, Object> model = modelAndView.getModel();
        for (Map.Entry<String, Object> modelEntry : model.entrySet()) {
            request.setAttribute(modelEntry.getKey(), modelEntry.getValue());
        }
        request.getRequestDispatcher(VIEW_PATH + viewPath).forward(request, response);
    }
}
