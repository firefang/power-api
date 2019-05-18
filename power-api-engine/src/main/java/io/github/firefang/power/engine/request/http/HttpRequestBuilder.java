package io.github.firefang.power.engine.request.http;

import java.util.Map;

import io.github.firefang.power.common.util.StringUtil;
import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IRequestBuilder;

/**
 * HTTP request build
 * 
 * @author xinufo
 *
 */
public class HttpRequestBuilder implements IRequestBuilder {
    private HttpRequestInfo request = new HttpRequestInfo();

    @Override
    public void setTarget(String basePath, String target) {
        String url;
        if (StringUtil.hasLength(target)) {
            // 接口URL为空
            url = basePath;
        } else if (target.startsWith("http")) {
            // 接口URL覆盖项目URL
            url = target;
        } else {
            url = basePath + target;
        }
        request.setUrl(url);
    }

    @Override
    public void setMethod(String method) {
        request.setMethod(method);
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        request.setHeaders(headers);
    }

    @Override
    public void setRequestParamTypes(String[] types) {
    }

    @Override
    public void setRequestParams(Object[] params) {
        request.setParams(params);
    }

    @Override
    public IRequestInfo build() {
        return request;
    }

}
