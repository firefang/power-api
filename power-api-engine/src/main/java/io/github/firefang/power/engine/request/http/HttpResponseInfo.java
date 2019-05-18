package io.github.firefang.power.engine.request.http;

import java.util.Map;

import io.github.firefang.power.engine.request.IResponseInfo;

/**
 * HTTP response information
 * 
 * @author xinufo
 *
 */
public class HttpResponseInfo implements IResponseInfo {
    private int status;
    private Map<String, String> headers;
    private String body;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpResponseInfo [status=" + status + ", headers=" + headers + ", body=" + body + "]";
    }

}
