package io.github.firefang.power.engine.step.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.step.entity.ProjectInfo;
import io.github.firefang.power.engine.util.Pair;

/**
 * Step of resolving headers
 * 
 * @author xinufo
 *
 */
public class ResolveHeaderStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "解析请求头";
    }

    @Override
    protected void handleProjectStartPlain(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        Map<String, ExpressionInfo> headers = info.getHeaders();
        Pair<Map<String, String>, Map<String, ExpressionInfo>> result = divideExpressions(headers);
        Map<String, String> resolved = new HashMap<>(
                cxt.getEvaluator().evaluateExpressions(info.getResolvedVars(), result.getFirst(), String.class));
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setHeaders(deplayed);
        setDependOnCases(info, deplayed.values());
        info.setResolvedHeaders(resolved);
    }

    @Override
    protected void handleProjectStart(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        Map<String, String> toResolve = translate(info.getHeaders());
        Map<String, String> resolved = cxt.getEvaluator().evaluateExpressions(info.getResolvedVars(), toResolve,
                String.class);
        info.getResolvedHeaders().putAll(resolved);
        info.setHeaders(null);
    }

    @Override
    protected void handleApiStartPlain(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        Map<String, ExpressionInfo> headers = info.getHeaders();
        Pair<Map<String, String>, Map<String, ExpressionInfo>> result = divideExpressions(headers);
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(),
                cxt.getApiInfo().getResolvedVars());
        Map<String, String> resolved = new HashMap<>(
                cxt.getEvaluator().evaluateExpressions(vars, result.getFirst(), String.class));
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setHeaders(deplayed);
        setDependOnCases(info, deplayed.values());
        info.setResolvedHeaders(resolved);
    }

    @Override
    protected void handleApiStart(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        Map<String, String> toResolve = translate(info.getHeaders());
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(),
                cxt.getApiInfo().getResolvedVars());
        Map<String, String> resolved = cxt.getEvaluator().evaluateExpressions(vars, toResolve, String.class);
        info.getResolvedHeaders().putAll(resolved);
        info.setHeaders(null);
    }

    @Override
    protected void handleCaseStartPlain(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        Map<String, ExpressionInfo> headers = info.getHeaders();
        Pair<Map<String, String>, Map<String, ExpressionInfo>> result = divideExpressions(headers);
        Map<String, Object> vars = collectAllVars(cxt);
        Map<String, String> resolved = new HashMap<>(
                cxt.getEvaluator().evaluateExpressions(vars, result.getFirst(), String.class));
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setHeaders(deplayed);
        setDependOnCases(info, deplayed.values());
        info.setResolvedHeaders(resolved);
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        Map<String, String> toResolve = translate(info.getHeaders());
        Map<String, Object> vars = collectAllVars(cxt);
        Map<String, String> resolved = cxt.getEvaluator().evaluateExpressions(vars, toResolve, String.class);
        Map<String, String> headers = info.getResolvedHeaders();
        headers.putAll(resolved);
        info.setHeaders(null);
        cxt.getBuilder().setHeaders(headers);
    }

}
