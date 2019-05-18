package io.github.firefang.power.engine.task;

import java.util.Set;

import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.result.FailureInfo;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.CaseInfo;

/**
 * @author xinufo
 *
 */
public class CaseTask extends BaseTask<CaseResult, PowerCaseDO> {
    private static final long serialVersionUID = 1L;
    private boolean runDependOnCaseMode;
    private CaseInfo info;

    public CaseTask(TaskContext taskContext, StepContext stepContext, PowerCaseDO entity, boolean runDependOnCaseMode) {
        super(taskContext, stepContext, entity);
        this.runDependOnCaseMode = runDependOnCaseMode;
    }

    @Override
    protected CaseResult compute() {
        try {
            info = chain.preprocessCase(stepContext, entity);
            stepContext.setCaseInfo(info);
        } catch (Exception e) {
            postMessage(this, msg(MSG_FAIL), entity);
            return failCasePreProcess(e, entity);
        }

        // start
        postMessage(this, msg(MSG_START), entity);
        CaseResult result = start();

        // end
        CaseResult endResult = end();
        if (!endResult.isSuccess()) {
            result.setSuccess(false);
            result.setFailureInfo(endResult.getFailureInfo());
            postMessage(this, msg(MSG_FAIL), entity);
        } else {
            postMessage(this, msg(MSG_FINISH), entity);
        }
        return result;
    }

    private CaseResult start() {
        CaseResult result = chain.caseStart(stepContext, true);
        try {
            handleDependOnCases();
            result = chain.caseStart(stepContext, false);
        } catch (DependOnCaseNotAllowedException e) {
            result.setSuccess(false);
            result.setFailureInfo(new FailureInfo(e, "处理依赖案例"));
        }
        return result;
    }

    private CaseResult end() {
        CaseResult result = chain.caseEnd(stepContext, true);
        try {
            handleDependOnCases();
            result = chain.caseEnd(stepContext, false);
        } catch (DependOnCaseNotAllowedException e) {
            result.setSuccess(false);
            result.setFailureInfo(new FailureInfo(e, "处理依赖案例"));
        }
        return result;
    }

    private void handleDependOnCases() throws DependOnCaseNotAllowedException {
        Set<String> dependOnCaseKeys = info.getDependOnCases();
        if (!dependOnCaseKeys.isEmpty()) {
            if (runDependOnCaseMode) {
                // 被依赖的案例中不允许再依赖案例
                throw new DependOnCaseNotAllowedException();
            } else {
                runDependOnCases(info, results -> info.getResolvedVars().put(EngineConstants.KEY_CASE_RESULTS, results));
            }
        }
    }

}
