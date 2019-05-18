package io.github.firefang.power.engine.step.entity;

import java.util.List;
import java.util.Map;

import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;

/**
 * @author xinufo
 *
 */
public class ProjectInfo extends BaseEntityInfo {
    private PowerProjectDO entity;
    private List<ExpressionInfo> setups;
    private List<ExpressionInfo> teardowns;
    private Map<String, ExpressionInfo> vars;
    private Map<String, ExpressionInfo> headers;

    private Map<String, Object> resolvedVars;
    private Map<String, String> resolvedHeaders;

    public ProjectInfo(PowerProjectDO entity, IExpressionEvaluator evaluator) throws Exception {
        super(evaluator);
        this.entity = entity;
        this.setups = processList(entity.getSetups(), evaluator);
        this.teardowns = processList(entity.getTeardowns(), evaluator);
        this.vars = processMap(entity.getVars(), evaluator);
        this.headers = processMap(entity.getHeaders(), evaluator);
    }

    public PowerProjectDO getEntity() {
        return entity;
    }

    public void setEntity(PowerProjectDO entity) {
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
