package io.github.firefang.power.engine.request.http;

import java.util.Map;

import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.IContentResolver;

/**
 * HTTP content type
 * 
 * @author xinufo
 *
 */
public enum HttpContentType implements IContentResolver {
    FORM {
        @SuppressWarnings("unchecked")
        @Override
        public Object[] resolve(Object params, String[] types, Map<String, Object> vars,
                IExpressionEvaluator evaluator) {
            Map<String, String> ps = (Map<String, String>) params;
            Map<String, String> result = evaluator.evaluateExpressions(vars, ps, String.class);
            return new Object[] { result };
        }
    },
    BODY {
        @Override
        public Object[] resolve(Object params, String[] types, Map<String, Object> vars,
                IExpressionEvaluator evaluator) {
            String p = (String) params;
            String value = evaluator.evaluateExpression(vars, p, String.class);
            return new Object[] { value };
        }
    };

}
