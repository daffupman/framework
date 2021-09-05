package io.daff.framework.mvc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型数据和视图
 *
 * @author daff
 * @since 2021/9/5
 */
public class ModelAndView {

    private String viewPath;
    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView setView(String viewPath) {
        this.viewPath = viewPath;
        return this;
    }

    public ModelAndView setModel(String attrName, Object attrValue) {
        model.put(attrName, attrValue);
        return this;
    }

    public String getViewPath() {
        return viewPath;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
