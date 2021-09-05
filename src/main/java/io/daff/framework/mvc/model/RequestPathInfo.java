package io.daff.framework.mvc.model;

import java.util.Objects;

/**
 * 请求的信息，包括请求方法和路径
 *
 * @author daff
 * @since 2021/9/5
 */
public class RequestPathInfo {

    private String httpMethod;
    private String httpPath;

    public RequestPathInfo() {
    }

    public RequestPathInfo(String httpMethod, String httpPath) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPathInfo that = (RequestPathInfo) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(httpPath, that.httpPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpPath);
    }
}
