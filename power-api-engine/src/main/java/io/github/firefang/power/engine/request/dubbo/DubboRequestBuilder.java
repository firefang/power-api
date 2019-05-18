package io.github.firefang.power.engine.request.dubbo;

import java.util.Map;

import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IRequestBuilder;

/**
 * Dubbo request builder
 * 
 * @author xinufo
 *
 */
public class DubboRequestBuilder implements IRequestBuilder {
    private DubboRequestInfo request = new DubboRequestInfo();

    @Override
    public void setTarget(String basePath, String target) {
        request.setRegisy(basePath);
        request.setClassName(target);
    }

    @Override
    public void setMethod(String method) {
        request.setMethod(method);
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
    }

    @Override
    public void setRequestParamTypes(String[] types) {
        request.setTypes(types);
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
