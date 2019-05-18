package io.github.firefang.power.engine.expression;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import io.github.firefang.power.engine.exception.EvaluateException;

/**
 * Expression evaluator
 * 
 * @author xinufo
 *
 */
public interface IExpressionEvaluator {

    /**
     * Register keywords. This method may be invoked more than one time, so ensure
     * clear old keywords before registering new keywords
     * 
     * @param keywords
     */
    void registerKeywords(Map<String, Method> keywords);

    /**
     * Pre-process the expression
     * 
     * @param expression
     * @return
     */
    ExpressionInfo preprocess(String expression) throws EvaluateException;

    /**
     * Evaluate variables. Before evaluating, the variables should be sorted by
     * dependencies
     * 
     * @param vars
     * @param resolvedVars
     * @return
     * @throws EvaluateException
     */
    Map<String, Object> evaluateVariables(Map<String, String> vars, Map<String, Object> resolvedVars)
            throws EvaluateException;

    /**
     * Evaluate a expression list using a resolved variables list
     * 
     * @param vars
     * @param exps
     * @param type
     * @return
     * @throws EvaluateException
     */
    <T> Map<String, T> evaluateExpressions(Map<String, Object> vars, Map<String, String> exps, Class<T> type)
            throws EvaluateException;

    /**
     * Evaluate a expression list using a resolved variables list
     * 
     * @param vars
     * @param exps
     * @throws EvaluateException
     */
    void evaluateExpressions(Map<String, Object> vars, List<String> exps) throws EvaluateException;

    /**
     * Evaluate a single expression using a resolved variables list
     * 
     * @param vars
     * @param exp
     * @param type
     * @return
     * @throws EvaluateException
     */
    <T> T evaluateExpression(Map<String, Object> vars, String exp, Class<T> type) throws EvaluateException;

    /**
     * Determine if the expression is a template
     * 
     * @param exp
     * @return
     */
    boolean isTemplate(String exp);

}
