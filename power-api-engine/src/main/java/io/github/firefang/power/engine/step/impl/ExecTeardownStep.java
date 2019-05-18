package io.github.firefang.power.engine.step.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.step.entity.ProjectInfo;
import io.github.firefang.power.engine.util.Pair;

/**
 * Step of executing setup and teardown
 * 
 * @author xinufo
 *
 */
public class ExecTeardownStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "执行Teardown";
    }

    @Override
    protected void handleCaseEndPlain(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        List<ExpressionInfo> teardowns = info.getTeardowns();
        Pair<List<String>, List<ExpressionInfo>> result = divideExpressions(teardowns);
        Map<String, Object> vars = collectAllVars(cxt);
        cxt.getEvaluator().evaluateExpressions(vars, result.getFirst());
        List<ExpressionInfo> deplayed = result.getSecond();
        info.setSetups(deplayed);
        setDependOnCases(info, deplayed);
    }

    @Override
    protected void handleCaseEnd(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        List<ExpressionInfo> teardowns = info.getTeardowns();
        List<String> toResolve = teardowns.stream().map(ExpressionInfo::getExpression).collect(Collectors.toList());
        Map<String, Object> vars = collectAllVars(cxt);
        cxt.getEvaluator().evaluateExpressions(vars, toResolve);
        info.setSetups(null);
    }

    @Override
    protected void handleApiEndPlain(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        List<ExpressionInfo> teardowns = info.getTeardowns();
        Pair<List<String>, List<ExpressionInfo>> result = divideExpressions(teardowns);
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        cxt.getEvaluator().evaluateExpressions(vars, result.getFirst());
        List<ExpressionInfo> deplayed = result.getSecond();
        info.setSetups(deplayed);
        setDependOnCases(info, deplayed);
    }

    @Override
    protected void handleApiEnd(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        List<ExpressionInfo> teardowns = info.getTeardowns();
        List<String> toResolve = teardowns.stream().map(ExpressionInfo::getExpression).collect(Collectors.toList());
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        cxt.getEvaluator().evaluateExpressions(vars, toResolve);
        info.setSetups(null);
    }

    @Override
    protected void handleProjectEndPlain(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        List<ExpressionInfo> teardowns = info.getTeardowns();
        List<String> toResolve = teardowns.stream().map(ExpressionInfo::getExpression).collect(Collectors.toList());
        cxt.getEvaluator().evaluateExpressions(info.getResolvedVars(), toResolve);
        info.setSetups(null);
    }

    @Override
    protected void handleProjectEnd(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        List<ExpressionInfo> teardowns = info.getTeardowns();
        Pair<List<String>, List<ExpressionInfo>> result = divideExpressions(teardowns);
        cxt.getEvaluator().evaluateExpressions(info.getResolvedVars(), result.getFirst());
        List<ExpressionInfo> deplayed = result.getSecond();
        info.setSetups(deplayed);
        setDependOnCases(info, deplayed);
    }

}
