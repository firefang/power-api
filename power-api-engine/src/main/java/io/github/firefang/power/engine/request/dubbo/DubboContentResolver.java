package io.github.firefang.power.engine.request.dubbo;

import java.util.Map;

import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.IContentResolver;

/**
 * Dubbo content resolver
 * 
 * @author xinufo
 *
 */
public class DubboContentResolver implements IContentResolver {

    @Override
    public Object[] resolve(Object params, String[] types, Map<String, Object> vars, IExpressionEvaluator evaluator) {
        String[] ps = (String[]) params;
        Object[] result = new Object[ps.length];
        for (int i = 0; i < ps.length; ++i) {
            Object value = evaluator.evaluateExpression(vars, ps[i], Object.class);
            // 当表达式为模板时会被解析为String类型
            // 若参数类型不是String则需要二次解析
            if (value instanceof String && !"java.lang.String".equals(types[i]) && evaluator.isTemplate(ps[i])) {
                value = evaluator.evaluateExpression(vars, (String) value, Object.class);
            }
            result[i] = value;
        }
        return result;
    }

}
