package io.github.firefang.power.engine.step.impl;

import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.AssertionFailure;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.step.IRunStage;
import io.github.firefang.power.engine.step.IStep;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Step of generate result
 * 
 * @author xinufo
 *
 */
public class ResultStep implements IStep, IRunStage {

    @Override
    public String name() {
        return "生成结果";
    }

    @Override
    public BaseResult process(StepContext cxt, IStepChain chain) {
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
            return handleCaseResult(cxt);
        default:
            throw new IllegalStateException("未知阶段: " + cxt.getStage());
        }

        result.setEntityId(id);
        result.setEntityName(name);

        result.setSuccess(true);
        return result;
    }

    private CaseResult handleCaseResult(StepContext cxt) {
        PowerCaseDO c = cxt.getCaseInfo().getEntity();
        CaseResult cr = new CaseResult();
        cr.setEntityId(c.getId());
        cr.setEntityName(c.getName());
        cr.setRequest(cxt.getRequest());
        cr.setResponse(cxt.getResponse());
        cr.setDuration(cxt.getDuration());
        // 处理验签结果
        boolean verifySignFailed = cxt.isVerifySignFailed();
        cr.setVerifySignFailed(verifySignFailed);
        // 处理断言结果
        AssertionFailure af = cxt.getAssertionFailure();
        cr.setAssertionFailure(af);

        cr.setSuccess(!verifySignFailed && af == null);
        return cr;
    }

}
