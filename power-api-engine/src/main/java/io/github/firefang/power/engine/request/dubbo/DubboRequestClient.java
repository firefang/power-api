package io.github.firefang.power.engine.request.dubbo;

import io.github.firefang.power.engine.request.IContentResolver;
import io.github.firefang.power.engine.request.IRequestBuilder;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IResponseInfo;

/**
 * Dubbo request client
 * 
 * @author xinufo
 *
 */
public class DubboRequestClient implements IRequestClient {
    private static final IContentResolver resolver = new DubboContentResolver();

    @Override
    public void init() {
    }

    @Override
    public void connect(IRequestInfo request) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public IResponseInfo request(IRequestInfo request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public IRequestBuilder newBuilder() {
        return new DubboRequestBuilder();
    }

    @Override
    public IContentResolver resolver(String type) {
        return resolver;
    }

}
