package io.github.firefang.power.engine.step.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.util.Pair;

/**
 * Step of resolving encrypt parameters
 * 
 * @author xinufo
 *
 */
public class ResolveEncryptParamsStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "解析加密参数";
    }

    @Override
    protected void handleApiStartPlain(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        Map<String, ExpressionInfo> encryptParams = info.getEncryptParams();
        Pair<Map<String, String>, Map<String, ExpressionInfo>> result = divideExpressions(encryptParams);
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        Map<String, Object> resolved = new HashMap<>(
                cxt.getEvaluator().evaluateExpressions(vars, result.getFirst(), Object.class));
        info.setResolvedEncryptParams(resolved);
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setEncryptParams(deplayed);
        setDependOnCases(info, deplayed.values());
    }

    @Override
    protected void handleApiStart(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        Map<String, String> toResolve = translate(info.getEncryptParams());
        Map<String, Object> resolved = cxt.getEvaluator().evaluateExpressions(vars, toResolve, Object.class);
        info.getResolvedEncryptParams().putAll(resolved);
        info.setEncryptParams(null);
    }

}
