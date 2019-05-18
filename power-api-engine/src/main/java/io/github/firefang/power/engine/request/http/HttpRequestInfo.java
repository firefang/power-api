package io.github.firefang.power.engine.request.http;

import java.util.Map;

import io.github.firefang.power.engine.request.IRequestInfo;

/**
 * HTTP request information
 * 
 * @author xinufo
 *
 */
public class HttpRequestInfo implements IRequestInfo {
    private String url;
    private String method;
    private Map<String, String> headers;
    private Object params; // Map<String, String> or String

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "HttpRequestInfo [url=" + url + ", method=" + method + ", headers=" + headers + ", params=" + params
                + "]";
    }

}
