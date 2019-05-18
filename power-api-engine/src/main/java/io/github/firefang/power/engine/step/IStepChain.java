package io.github.firefang.power.engine.step;

import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.step.entity.ProjectInfo;

/**
 * Chain of steps
 * 
 * @author xinufo
 *
 */
public interface IStepChain {

    ProjectInfo preprocessProject(StepContext cxt, PowerProjectDO entity) throws Exception;

    AggregateResult projectStart(StepContext cxt, boolean skipDependOnCases);

    ApiInfo preprocessApi(StepContext cxt, PowerApiDO entity) throws Exception;

    AggregateResult apiStart(StepContext cxt, boolean skipDependOnCases);

    CaseInfo preprocessCase(StepContext cxt, PowerCaseDO entity) throws Exception;

    CaseResult caseStart(StepContext cxt, boolean skipDependOnCases);

    CaseResult caseEnd(StepContext cxt, boolean skipDependOnCases);

    AggregateResult apiEnd(StepContext cxt, boolean skipDependOnCases);

    AggregateResult projectEnd(StepContext cxt, boolean skipDependOnCases);

    BaseResult process(StepContext cxt);

}
