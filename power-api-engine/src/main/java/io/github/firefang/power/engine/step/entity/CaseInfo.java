package io.github.firefang.power.engine.step.entity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.common.util.EnumUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.RequestContentParser;

/**
 * @author xinufo
 *
 */
public class CaseInfo extends BaseEntityInfo {
    private PowerCaseDO entity;
    private List<ExpressionInfo> setups;
    private List<ExpressionInfo> teardowns;
    private Map<String, ExpressionInfo> vars;
    private Map<String, ExpressionInfo> headers;
    private Map<String, ExpressionInfo> requestParams;
    private List<ExpressionInfo> assertions;

    private Map<String, Object> resolvedVars;
    private Map<String, String> resolvedHeaders;

    public CaseInfo(PowerCaseDO entity, IExpressionEvaluator evaluator) throws Exception {
        super(evaluator);
        this.entity = entity;
        this.setups = processList(entity.getSetups(), evaluator);
        this.teardowns = processList(entity.getTeardowns(), evaluator);
        this.vars = processMap(entity.getVars(), evaluator);
        this.headers = processMap(entity.getHeaders(), evaluator);
        this.assertions = processList(entity.getAssertions(), evaluator);
        handleRequestParams(evaluator);
    }

    private void handleRequestParams(IExpressionEvaluator evaluator) {
        Map<String, Object> ps = entity.getRequestParams();
        if (CollectionUtil.isEmpty(ps)) {
            this.requestParams = Collections.emptyMap();
        } else {
            String type = (String) ps.get(EngineConstants.KEY_REQUEST_TYPE);
            RequestContentParser rct = EnumUtil.fromString(RequestContentParser.class, type);
            if (rct == null) {
                throw new IllegalArgumentException("不支持的报文类型: " + type);
            }
            Map<String, String> exps = rct.getExpressions(ps.get(EngineConstants.KEY_REQUEST_CONTENT));
            this.requestParams = processMap(exps, evaluator);
        }
    }

    public PowerCaseDO getEntity() {
        return entity;
    }

    public void setEntity(PowerCaseDO entity) {
        this.entity = entity;
    }

    public List<ExpressionInfo> getSetups() {
        return setups;
    }

    public void setSetups(List<ExpressionInfo> setups) {
        this.setups = setups;
    }

    public List<ExpressionInfo> getTeardowns() {
        return teardowns;
    }

    public void setTeardowns(List<ExpressionInfo> teardowns) {
        this.teardowns = teardowns;
    }

    public Map<String, ExpressionInfo> getVars() {
        return vars;
    }

    public void setVars(Map<String, ExpressionInfo> vars) {
        this.vars = vars;
    }

    public Map<String, ExpressionInfo> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, ExpressionInfo> headers) {
        this.headers = headers;
    }

    public Map<String, ExpressionInfo> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, ExpressionInfo> requestParams) {
        this.requestParams = requestParams;
    }

    public List<ExpressionInfo> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<ExpressionInfo> assertions) {
        this.assertions = assertions;
    }

    public Map<String, Object> getResolvedVars() {
        return resolvedVars;
    }

    public void setResolvedVars(Map<String, Object> resolvedVars) {
        this.resolvedVars = resolvedVars;
    }

    public Map<String, String> getResolvedHeaders() {
        return resolvedHeaders;
    }

    public void setResolvedHeaders(Map<String, String> resolvedHeaders) {
        this.resolvedHeaders = resolvedHeaders;
    }

}
