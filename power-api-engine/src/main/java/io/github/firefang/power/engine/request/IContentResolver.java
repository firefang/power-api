package io.github.firefang.power.engine.request;

import java.util.Map;

import io.github.firefang.power.engine.expression.IExpressionEvaluator;

/**
 * Interface used to resolve the expressions in the request parameters
 * 
 * @author xinufo
 *
 */
public interface IContentResolver {

    /**
     * Resolve the expression
     * 
     * @param params request parameters
     * @param types types of request parameters
     * @param vars variables used to resolve expressions
     * @param evaluator evaluator used to resolve expressions
     * @return
     */
    Object[] resolve(Object params, String[] types, Map<String, Object> vars, IExpressionEvaluator evaluator);

}
