package io.github.firefang.power.engine.step.impl;

import java.util.Map;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.exception.UnsupportedContentTypeException;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.IContentResolver;
import io.github.firefang.power.engine.request.IRequestBuilder;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.util.Pair;

/**
 * Step of resolving request parameters
 * 
 * @author xinufo
 *
 */
public class ResolveRequestParamsStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "解析请求参数";
    }

    @Override
    protected void handleCaseStartPlain(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        Map<String, ExpressionInfo> requestParams = info.getRequestParams();
        Pair<Map<String, String>, Map<String, ExpressionInfo>> result = divideExpressions(requestParams);
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        // 不做解析，仅找出依赖的案例
        setDependOnCases(info, deplayed.values());
        info.setRequestParams(null);
    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        Map<String, Object> requestParamMap = cxt.getCaseInfo().getEntity().getRequestParams();

        IRequestBuilder builder = cxt.getBuilder();

        if (!CollectionUtil.isEmpty(requestParamMap)) {
            IRequestClient client = cxt.getClient();
            String typeName = (String) requestParamMap.get(EngineConstants.KEY_REQUEST_TYPE);
            IContentResolver resolver = client.resolver(typeName);
            if (resolver == null) {
                throw new UnsupportedContentTypeException(typeName);
            }

            IExpressionEvaluator evaluator = cxt.getEvaluator();
            Map<String, Object> vars = collectAllVars(cxt);

            String[] types = cxt.getApiInfo().getEntity().getParamTypes();
            Object params = requestParamMap.get(EngineConstants.KEY_REQUEST_CONTENT);
            Object[] resolvedParams = resolver.resolve(params, types, vars, evaluator);

            builder.setRequestParams(resolvedParams);
        }

        cxt.setRequest(builder.build());
    }

}
