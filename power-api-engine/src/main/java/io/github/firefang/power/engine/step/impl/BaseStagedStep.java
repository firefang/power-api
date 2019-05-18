package io.github.firefang.power.engine.step.impl;

import java.util.concurrent.CancellationException;

import io.github.firefang.power.engine.event.PowerEvent;
import io.github.firefang.power.engine.event.PowerEventBus;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.step.IStep;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.IRunStage;
import io.github.firefang.power.engine.step.StepContext;

/**
 * Base class for steps
 * 
 * @author xinufo
 *
 */
public abstract class BaseStagedStep implements IStep, IRunStage {

    @Override
    public BaseResult process(StepContext cxt, IStepChain chain) {
        if (cxt.isCancelled()) {
            throw new CancellationException();
        }

        byte stage = cxt.getStage();
        PowerEventBus eventBus = cxt.getEventBus();
        String name = this.name();
        eventBus.post(new PowerEvent(this, PowerEvent.LEVEL_DEBUG, "开始" + name));

        switch (stage) {
        case PROJECT_START_PLAIN:
            handleProjectStartPlain(cxt);
            break;
        case PROJECT_START:
            handleProjectStart(cxt);
            break;
        case PROJECT_END_PLAIN:
            handleProjectEndPlain(cxt);
            break;
        case PROJECT_END:
            handleProjectEnd(cxt);
            break;
        case API_START_PLAIN:
            handleApiStartPlain(cxt);
            break;
        case API_START:
            handleApiStart(cxt);
            break;
        case API_END_PLAIN:
            handleApiEndPlain(cxt);
            break;
        case API_END:
            handleApiEnd(cxt);
            break;
        case CASE_START_PLAIN:
            handleCaseStartPlain(cxt);
            break;
        case CASE_START:
            handleCaseStart(cxt);
            break;
        case CASE_END_PLAIN:
            handleCaseEndPlain(cxt);
            break;
        case CASE_END:
            handleCaseEnd(cxt);
            break;
        default:
            throw new IllegalStateException("未知阶段: " + stage);
        }
        eventBus.post(new PowerEvent(this, PowerEvent.LEVEL_DEBUG, "完成" + name));
        return callNextOrReturn(cxt, chain);
    }

    protected void handleProjectStartPlain(StepContext cxt) {
    }

    protected void handleProjectStart(StepContext cxt) {
    }

    protected void handleApiStartPlain(StepContext cxt) {
    }

    protected void handleApiStart(StepContext cxt) {
    }

    protected void handleCaseStartPlain(StepContext cxt) {
    }

    protected void handleCaseStart(StepContext cxt) {
    }

    protected void handleCaseEndPlain(StepContext cxt) {
    }

    protected void handleCaseEnd(StepContext cxt) {
    }

    protected void handleApiEndPlain(StepContext cxt) {
    }

    protected void handleApiEnd(StepContext cxt) {
    }

    protected void handleProjectEndPlain(StepContext cxt) {
    }

    protected void handleProjectEnd(StepContext cxt) {
    }

    protected BaseResult callNextOrReturn(StepContext cxt, IStepChain chain) {
        return chain.process(cxt);
    }

}
