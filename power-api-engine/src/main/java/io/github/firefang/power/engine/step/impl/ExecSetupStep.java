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
 * Step of executing setup
 * 
 * @author xinufo
 *
 */
public class ExecSetupStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "执行Setup";
    }

    @Override
    protected void handleProjectStartPlain(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        List<ExpressionInfo> setups = info.getSetups();
        Pair<List<String>, List<ExpressionInfo>> result = divideExpressions(setups);
        // execute setups
        cxt.getEvaluator().evaluateExpressions(info.getResolvedVars(), result.getFirst());
        List<ExpressionInfo> deplayed = result.getSecond();
        info.setSetups(deplayed);
        setDependOnCases(info, deplayed);
    }

    @Override
    protected void handleProjectStart(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        List<ExpressionInfo> setups = info.getSetups();
        List<String> toResolve = setups.stream().map(ExpressionInfo::getExpression).collect(Collectors.toList());
        cxt.getEvaluator().evaluateExpressions(info.getResolvedVars(), toResolve);
        info.setSetups(null);
    }

    @Override
    protected void handleApiStartPlain(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        List<ExpressionInfo> setups = info.getSetups();
        Pair<List<String>, List<ExpressionInfo>> result = divideExpressions(setups);
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        cxt.getEvaluator().evaluateExpressions(vars, result.getFirst());
        List<ExpressionInfo> deplayed = result.getSecond();
        info.setSetups(deplayed);
        setDependOnCases(info, deplayed);
    }

    @Override
    protected void handleApiStart(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        List<ExpressionInfo> setups = info.getSetups();
        List<String> toResolve = setups.stream().map(ExpressionInfo::getExpression).collect(Collectors.toList());
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        cxt.getEvaluator().evaluateExpressions(vars, toResolve);
        info.setSetups(null);
    }

    @Override
    protected void handleCaseStartPlain(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        List<ExpressionInfo> setups = info.getSetups();
        Pair<List<String>, List<ExpressionInfo>> result = divideExpressions(setups);
        Map<String, Object> vars = collectAllVars(cxt);
        cxt.getEvaluator().evaluateExpressions(vars, result.getFirst());
        List<ExpressionInfo> deplayed = result.getSecond();
        info.setSetups(deplayed);
        setDependOnCases(info, deplayed);
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        List<ExpressionInfo> setups = info.getSetups();
        List<String> toResolve = setups.stream().map(ExpressionInfo::getExpression).collect(Collectors.toList());
        Map<String, Object> vars = collectAllVars(cxt);
        cxt.getEvaluator().evaluateExpressions(vars, toResolve);
        info.setSetups(null);
    }

}
