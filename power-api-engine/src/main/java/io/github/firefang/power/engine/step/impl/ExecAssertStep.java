package io.github.firefang.power.engine.step.impl;

import java.util.List;
import java.util.Map;

import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.IResponseInfo;
import io.github.firefang.power.engine.result.AssertionFailure;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.CaseInfo;

/**
 * Step of executing assertions
 * 
 * @author xinufo
 *
 */
public class ExecAssertStep extends BaseExpressionStep {
    public static final String NAME = "执行断言";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        if (cxt.getResponse() == null) {
            return;
        }

        CaseInfo info = cxt.getCaseInfo();
        List<ExpressionInfo> exps = info.getAssertions();
        for (ExpressionInfo exp : exps) {
            if (!exp.getDependedCaseKeys().isEmpty()) {
                throw new IllegalArgumentException("断言中不允许依赖案例: " + exp.getExpression());
            }
        }

        IResponseInfo response = cxt.getResponse();
        Map<String, Object> vars = collectAllVars(cxt);
        vars.put(EngineConstants.KEY_RESPONSE_VAR_NAME, response);

        List<String> assertions = info.getEntity().getAssertions();
        IExpressionEvaluator evaluator = cxt.getEvaluator();

        for (String exp : assertions) {
            Boolean result;
            try {
                result = evaluator.evaluateExpression(vars, exp, Boolean.class);
            } catch (Exception e) {
                cxt.setAssertionFailure(new AssertionFailure(exp, "执行断言失败", e));
                return;
            }
            if (result == null) {
                cxt.setAssertionFailure(new AssertionFailure(exp, "断言表达式必须返回布尔型结果", null));
                return;
            }
            if (!result) {
                cxt.setAssertionFailure(new AssertionFailure(exp, "断言不通过", null));
                return;
            }
        }
    }

}
