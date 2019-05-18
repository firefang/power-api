package io.github.firefang.power.engine.step.impl;

import java.util.ArrayList;
import java.util.List;

import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.step.IRunStage;
import io.github.firefang.power.engine.step.IStep;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.step.entity.ProjectInfo;

/**
 * Default implementation of IStepChain. This class is NOT thread safe
 * 
 * @author xinufo
 *
 */
public class DefaultStepChain implements IStepChain, IRunStage {
    private static List<IStep> projectStartSteps;
    private static List<IStep> apiStartSteps;
    private static List<IStep> caseStartSteps;
    private static List<IStep> caseEndSteps;
    private static List<IStep> apiEndSteps;
    private static List<IStep> projectEndSteps;

    private List<IStep> steps;
    private int current;

    public DefaultStepChain() {
        initProjectStartSteps();
        initApiStartSteps();
        initCaseStartSteps();
        initCaseEndSteps();
        initApiEndSteps();
        initProjectEndSteps();
    }

    public static void initProjectStartSteps() {
        projectStartSteps = new ArrayList<>();
        projectStartSteps.add(new HandleExceptionStep());
        projectStartSteps.add(new ResolveVarsStep());
        projectStartSteps.add(new ResolveHeaderStep());
        projectStartSteps.add(new ExecSetupStep());
        projectStartSteps.add(new ResultStep());
    }

    public static void initApiStartSteps() {
        apiStartSteps = new ArrayList<>();
        apiStartSteps.add(new HandleExceptionStep());
        apiStartSteps.add(new ResolveVarsStep());
        apiStartSteps.add(new ResolveApiTypeStep());
        apiStartSteps.add(new ResolveHeaderStep());
        apiStartSteps.add(new ResolveEncryptParamsStep());
        apiStartSteps.add(new ExecSetupStep());
        apiStartSteps.add(new ResultStep());
    }

    public static void initCaseStartSteps() {
        caseStartSteps = new ArrayList<>();
        caseStartSteps.add(new HandleExceptionStep());
        caseStartSteps.add(new ResolveVarsStep());
        caseStartSteps.add(new ResolveHeaderStep());
        caseStartSteps.add(new ResolveApiTargetStep());
        caseStartSteps.add(new ResolveRequestParamsStep());
        caseStartSteps.add(new ExecEncryptStep());
        caseStartSteps.add(new ExecSetupStep());
        caseStartSteps.add(new ExecRequestStep());
        caseStartSteps.add(new ResultStep());
    }

    public static void initCaseEndSteps() {
        caseEndSteps = new ArrayList<>();
        caseEndSteps.add(new HandleExceptionStep());
        caseEndSteps.add(new ExecDecryptStep());
        caseEndSteps.add(new ExecAssertStep());
        caseEndSteps.add(new ExecTeardownStep());
        caseEndSteps.add(new ResultStep());
    }

    public static void initApiEndSteps() {
        apiEndSteps = new ArrayList<>();
        apiEndSteps.add(new HandleExceptionStep());
        apiEndSteps.add(new ExecTeardownStep());
        apiEndSteps.add(new ResultStep());
    }

    public static void initProjectEndSteps() {
        projectEndSteps = new ArrayList<>();
        projectEndSteps.add(new HandleExceptionStep());
        projectEndSteps.add(new ExecTeardownStep());
        projectEndSteps.add(new ResultStep());
    }

    @Override
    public ProjectInfo preprocessProject(StepContext cxt, PowerProjectDO entity) throws Exception {
        return new ProjectInfo(entity, cxt.getEvaluator());
    }

    @Override
    public AggregateResult projectStart(StepContext cxt, boolean skipDependOnCases) {
        byte stage = skipDependOnCases ? PROJECT_START_PLAIN : PROJECT_START;
        cxt.setStage(stage);
        current = 0;
        determinSteps(cxt);
        return (AggregateResult) process(cxt);
    }

    @Override
    public ApiInfo preprocessApi(StepContext cxt, PowerApiDO entity) throws Exception {
        return new ApiInfo(entity, cxt.getEvaluator());
    }

    @Override
    public AggregateResult apiStart(StepContext cxt, boolean skipDependOnCases) {
        byte stage = skipDependOnCases ? API_START_PLAIN : API_START;
        cxt.setStage(stage);
        current = 0;
        determinSteps(cxt);
        return (AggregateResult) process(cxt);
    }

    @Override
    public CaseInfo preprocessCase(StepContext cxt, PowerCaseDO entity) throws Exception {
        return new CaseInfo(entity, cxt.getEvaluator());
    }

    @Override
    public CaseResult caseStart(StepContext cxt, boolean skipDependOnCases) {
        byte stage = skipDependOnCases ? CASE_START_PLAIN : CASE_START;
        cxt.setStage(stage);
        current = 0;
        determinSteps(cxt);
        return (CaseResult) process(cxt);
    }

    @Override
    public CaseResult caseEnd(StepContext cxt, boolean skipDependOnCases) {
        byte stage = skipDependOnCases ? CASE_END_PLAIN : CASE_END;
        cxt.setStage(stage);
        current = 0;
        determinSteps(cxt);
        return (CaseResult) process(cxt);
    }

    @Override
    public AggregateResult apiEnd(StepContext cxt, boolean skipDependOnCases) {
        byte stage = skipDependOnCases ? API_END_PLAIN : API_END;
        cxt.setStage(stage);
        current = 0;
        determinSteps(cxt);
        return (AggregateResult) process(cxt);
    }

    @Override
    public AggregateResult projectEnd(StepContext cxt, boolean skipDependOnCases) {
        byte stage = skipDependOnCases ? PROJECT_END_PLAIN : PROJECT_END;
        cxt.setStage(stage);
        current = 0;
        determinSteps(cxt);
        return (AggregateResult) process(cxt);
    }

    @Override
    public BaseResult process(StepContext cxt) {
        if (current < steps.size()) {
            IStep step = steps.get(current++);
            cxt.setCurrentStep(step.name());
            return step.process(cxt, this);
        }
        throw new IllegalStateException("所有步骤均已执行，最后的步骤不应该再调用 IStepChain#process()");
    }

    private void determinSteps(StepContext cxt) {
        byte stage = cxt.getStage();

        switch (stage) {
        case PROJECT_START_PLAIN:
        case PROJECT_START:
            steps = projectStartSteps;
            break;
        case PROJECT_END_PLAIN:
        case PROJECT_END:
            steps = projectEndSteps;
            break;
        case API_START_PLAIN:
        case API_START:
            steps = apiStartSteps;
            break;
        case API_END_PLAIN:
        case API_END:
            steps = apiEndSteps;
            break;
        case CASE_START_PLAIN:
        case CASE_START:
            steps = caseStartSteps;
            break;
        case CASE_END_PLAIN:
        case CASE_END:
            steps = caseEndSteps;
            break;
        default:
            throw new IllegalStateException("未知阶段: " + stage);
        }
    }

}
