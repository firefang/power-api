package io.github.firefang.power.engine.step.impl;

import io.github.firefang.power.common.util.EnumUtil;
import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.exception.UnsupportedRequestTypeException;
import io.github.firefang.power.engine.request.IRequestBuilder;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.request.RequestClients;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Step of resolving the type of an api entity
 * 
 * @author xinufo
 *
 */
public class ResolveApiTypeStep extends BaseStagedStep {

    @Override
    public String name() {
        return "解析接口信息";
    }

    @Override
    protected void handleApiStart(StepContext cxt) {
        PowerApiDO entity = cxt.getApiInfo().getEntity();
        IRequestClient client = getClient(cxt, entity.getType());

        IRequestBuilder builder = client.newBuilder();
        builder.setMethod(entity.getMethod());
        builder.setRequestParamTypes(entity.getParamTypes());

        cxt.setClient(client);
        cxt.setBuilder(builder);
    }

    private IRequestClient getClient(StepContext cxt, String type) {
        String key = type.toUpperCase();
        return (IRequestClient) cxt.getShare().computeIfAbsent(key, k -> {
            RequestClients rc = EnumUtil.fromString(RequestClients.class, key);
            if (rc == null) {
                throw new UnsupportedRequestTypeException(key);
            }
            IRequestClient client = rc.createClient();
            client.init();
            return client;
        });
    }

}
