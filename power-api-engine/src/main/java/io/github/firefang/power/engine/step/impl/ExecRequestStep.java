package io.github.firefang.power.engine.step.impl;

import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IResponseInfo;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Step of sending a request
 * 
 * @author xinufo
 *
 */
public class ExecRequestStep extends BaseStagedStep {

    @Override
    public String name() {
        return "发送请求";
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        IRequestClient client = cxt.getClient();
        IRequestInfo request = cxt.getRequest();
        long start = System.currentTimeMillis();
        client.connect(request);
        IResponseInfo response = client.request(request);
        cxt.setDuration(System.currentTimeMillis() - start);
        client.close();
        cxt.setResponse(response);
    }

}
