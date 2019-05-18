package io.github.firefang.power.engine.step.impl;

import java.util.Map;
import java.util.Set;

import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.IRequestBuilder;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;

/**
 * Step of resolving the target of an api entity
 * 
 * @author xinufo
 *
 */
public class ResolveApiTargetStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "解析接口地址";
    }

    @Override
    protected void handleCaseStartPlain(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        ExpressionInfo target = info.getTarget();
        Set<String> dcs = target.getDependedCaseKeys();
        if (!dcs.isEmpty()) {
            info.getDependOnCases().addAll(dcs);
        }
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        PowerProjectDO project = cxt.getProjectInfo().getEntity();
        String basePath = project.getBasePath();

        String target = cxt.getApiInfo().getTarget().getExpression();
        target = resolveUrl(target, cxt);

        IRequestBuilder builder = cxt.getBuilder();
        builder.setTarget(basePath, target);
    }

    private String resolveUrl(String url, StepContext cxt) {
        Map<String, Object> vars = collectAllVars(cxt);
        IExpressionEvaluator evaluator = cxt.getEvaluator();
        return evaluator.evaluateExpression(vars, url, String.class);
    }

}
