package io.github.firefang.power.engine.expression.impl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.BeanReference;
import org.springframework.expression.spel.ast.VariableReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.exception.DependencyNotExistException;
import io.github.firefang.power.engine.exception.EvaluateException;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.result.BaseResult;

/**
 * IExpressionEvaluator implemented by Spring EL
 * 
 * @author xinufo
 *
 */
public class SpringELEvalator implements IExpressionEvaluator {
    private ExpressionParser parser = new SpelExpressionParser();
    private Map<String, Method> keywords;

    @Override
    public void registerKeywords(Map<String, Method> keywords) {
        this.keywords = keywords;
    }

    @Override
    public ExpressionInfo preprocess(String expression) throws EvaluateException {
        final ExpressionInfo info = new ExpressionInfo();
        info.setExpression(expression);
        final Set<String> dependencies = new HashSet<>(CollectionUtil.MAP_DEFAULT_SIZE);
        info.setDependencies(dependencies);
        final Set<String> dependedCaseKeys = new HashSet<>(CollectionUtil.MAP_DEFAULT_SIZE);
        info.setDependedCaseKeys(dependedCaseKeys);
        try {
            Expression exp = parser.parseExpression(expression, new DynamicParserContext(expression));
            Consumer<SpelNode> setter = (n) -> {
                if (n instanceof BeanReference) {
                    BeanReference br = (BeanReference) n;
                    dependedCaseKeys.add(br.toStringAST().substring(1)); // toStringAST -> @beanName
                }
                if (n instanceof VariableReference) {
                    VariableReference vr = (VariableReference) n;
                    dependencies.add(vr.toStringAST().substring(1)); // toStringAST -> #name
                }
            };
            iterate(exp, setter);
        } catch (Exception e) {
            throw new EvaluateException(expression, e);
        }
        return info;
    }

    @Override
    public Map<String, Object> evaluateVariables(Map<String, String> vars, Map<String, Object> resolvedVars)
            throws EvaluateException {
        Map<String, BaseResult> caseResults = getCaseResults(resolvedVars);
        StandardEvaluationContext cxt = createContext(resolvedVars, caseResults);
        Map<String, Object> varMap = new HashMap<>(CollectionUtil.mapSize(vars.size()));
        String name, exp;
        Object value;

        for (Map.Entry<String, String> e : vars.entrySet()) {
            name = e.getKey();
            exp = e.getValue();
            value = parseExpression(exp, cxt, Object.class);
            // add variable
            cxt.setVariable(name, value);
            // add result
            varMap.put(name, value);
        }
        // 返回不可变Map，防止被意外修改
        return Collections.unmodifiableMap(varMap);
    }

    @Override
    public <T> Map<String, T> evaluateExpressions(Map<String, Object> vars, Map<String, String> exps, Class<T> type)
            throws EvaluateException {
        Map<String, BaseResult> caseResults = getCaseResults(vars);
        StandardEvaluationContext cxt = createContext(vars, caseResults);

        Map<String, T> ret = new HashMap<>(CollectionUtil.mapSize(vars.size()));
        String name, exp;
        T value;

        for (Map.Entry<String, String> e : exps.entrySet()) {
            name = e.getKey();
            exp = e.getValue();
            value = parseExpression(exp, cxt, type);
            ret.put(name, value);
        }
        return Collections.unmodifiableMap(ret);
    }

    public void evaluateExpressions(Map<String, Object> vars, List<String> exps) throws EvaluateException {
        Map<String, BaseResult> caseResults = getCaseResults(vars);
        StandardEvaluationContext cxt = createContext(vars, caseResults);
        for (String exp : exps) {
            parseExpression(exp, cxt, Object.class);
        }
    }

    @Override
    public <T> T evaluateExpression(Map<String, Object> vars, String exp, Class<T> type) throws EvaluateException {
        Map<String, BaseResult> caseResults = getCaseResults(vars);
        StandardEvaluationContext cxt = createContext(vars, caseResults);
        return parseExpression(exp, cxt, type);
    }

    @Override
    public boolean isTemplate(String exp) {
        return DynamicParserContext.isTemplate(exp);
    }

    @SuppressWarnings("unchecked")
    private Map<String, BaseResult> getCaseResults(Map<String, Object> vars) {
        if (CollectionUtil.isEmpty(vars)) {
            return null;
        }
        Map<String, BaseResult> caseResults = (Map<String, BaseResult>) vars.get(EngineConstants.KEY_CASE_RESULTS);
        return caseResults;
    }

    private StandardEvaluationContext createContext(Map<String, Object> vars, Map<String, BaseResult> caseResults) {
        StandardEvaluationContext cxt = new StandardEvaluationContext();

        if (!CollectionUtil.isEmpty(vars)) {
            cxt.setVariables(vars);
        }
        if (!CollectionUtil.isEmpty(caseResults)) {
            cxt.setBeanResolver(new DependedCaseResolver(caseResults));
        }
        if (keywords != null) {
            for (Map.Entry<String, Method> e : keywords.entrySet()) {
                cxt.registerFunction(e.getKey(), e.getValue());
            }
        }
        return cxt;
    }

    private <T> T parseExpression(String expression, EvaluationContext cxt, Class<T> type) throws EvaluateException {
        try {
            ParserContext pc = new DynamicParserContext(expression);
            Expression exp = parser.parseExpression(expression, pc);
            return exp.getValue(cxt, type);
        } catch (DependencyNotExistException | EvaluateException e1) {
            throw e1;
        } catch (Exception e2) {
            throw new EvaluateException(expression, e2);
        }
    }

    /**
     * 递归遍历所有AST节点，会跳过字面量表达式(LiteralExpression)
     * 
     * @param exp
     * @param consumer
     */
    private void iterate(Expression exp, Consumer<SpelNode> consumer) {
        if (exp instanceof SpelExpression) {
            iterate(((SpelExpression) exp).getAST(), consumer);
        } else if (exp instanceof CompositeStringExpression) {
            Expression[] es = ((CompositeStringExpression) exp).getExpressions();
            for (Expression e : es) {
                iterate(e, consumer);
            }
        }
    }

    /**
     * 递归遍历所有AST节点
     * 
     * @param root
     * @param consumer
     */
    private void iterate(SpelNode root, Consumer<SpelNode> consumer) {
        consumer.accept(root);
        for (int i = 0; i < root.getChildCount(); ++i) {
            iterate(root.getChild(i), consumer);
        }
    }

}
