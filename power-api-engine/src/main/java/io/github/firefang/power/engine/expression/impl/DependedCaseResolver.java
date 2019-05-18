package io.github.firefang.power.engine.expression.impl;

import java.util.Map;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

import io.github.firefang.power.engine.exception.DependencyNotExistException;
import io.github.firefang.power.engine.exception.EvaluateException;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;

/**
 * BeanResolver used to run depended cases
 * 
 * @author xinufo
 *
 */
public class DependedCaseResolver implements BeanResolver {
    private Map<String, BaseResult> results;

    public DependedCaseResolver(Map<String, BaseResult> results) {
        this.results = results;
    }

    @Override
    public Object resolve(EvaluationContext context, String beanName) throws AccessException {
        BaseResult result = results.get(beanName);
        return unpackResult(result, beanName);
    }

    private CaseResult unpackResult(BaseResult result, String beanName) {
        if (result == null) {
            throw new DependencyNotExistException(beanName);
        }
        if (result instanceof CaseResult) {
            if (result.isSuccess()) {
                return (CaseResult) result;
            }
        } else {
            while ((result instanceof AggregateResult) && result.isSuccess()) {
                AggregateResult ar = (AggregateResult) result;
                result = ar.getChildren().get(0);
            }
            if (result.isSuccess()) {
                // 结果成功退出循环说明结果类型是CaseResult
                return (CaseResult) result;
            }
        }
        throw new EvaluateException("@" + beanName, result.getFailureInfo().getException());
    }

}
