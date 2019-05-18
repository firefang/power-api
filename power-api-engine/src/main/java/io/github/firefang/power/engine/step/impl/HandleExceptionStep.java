package io.github.firefang.power.engine.step.impl;

import java.util.Map;

import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.request.RequestClients;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.result.FailureInfo;
import io.github.firefang.power.engine.step.IRunStage;
import io.github.firefang.power.engine.step.IStep;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Step of handling exceptions
 * 
 * @author xinufo
 *
 */
public class HandleExceptionStep implements IStep, IRunStage {

    @Override
    public String name() {
        return "开启异常捕获";
    }

    @Override
    public BaseResult process(StepContext cxt, IStepChain chain) {
        try {
            BaseResult result = chain.process(cxt);
            if (cxt.getStage() == PROJECT_END) {
                destroyClients(cxt);
            }
            return result;
        } catch (Exception e) {
            BaseResult result;
            Integer id;
            String name;

            switch (cxt.getStage()) {
            case PROJECT_START_PLAIN:
            case PROJECT_START:
            case PROJECT_END_PLAIN:
            case PROJECT_END:
                PowerProjectDO p = cxt.getProjectInfo().getEntity();
                id = p.getId();
                name = p.getName();
                result = new AggregateResult();
                break;
            case API_START_PLAIN:
            case API_START:
            case API_END_PLAIN:
            case API_END:
                PowerApiDO a = cxt.getApiInfo().getEntity();
                id = a.getId();
                name = a.getName();
                result = new AggregateResult();
                break;
            case CASE_START_PLAIN:
            case CASE_START:
            case CASE_END_PLAIN:
            case CASE_END:
                PowerCaseDO c = cxt.getCaseInfo().getEntity();
                id = c.getId();
                name = c.getName();
                CaseResult cr = new CaseResult();
                cr.setRequest(cxt.getRequest());
                cr.setResponse(cxt.getResponse());
                result = cr;
                break;
            default:
                throw new IllegalStateException("未知阶段: " + cxt.getStage());
            }

            result.setEntityId(id);
            result.setEntityName(name);
            result.setSuccess(false);
            FailureInfo fi = new FailureInfo(e, cxt.getCurrentStep());
            result.setFailureInfo(fi);
            return result;
        }
    }

    private void destroyClients(StepContext cxt) {
        try {
            Map<String, Object> share = cxt.getShare();
            for (RequestClients rc : RequestClients.values()) {
                IRequestClient client = (IRequestClient) share.get(rc.name());
                if (client != null) {
                    client.destroy();
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

}
