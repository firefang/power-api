package io.github.firefang.power.engine.entity;

import java.util.List;
import java.util.Map;

/**
 * Entity of api
 * 
 * @author xinufo
 */
public class PowerApiDO extends BaseEntity {
    private Integer projectId;
    private String target; // url or dubbo class
    private String method;
    private String type;

    private List<String> setups;
    private List<String> teardowns;
    private Map<String, String> vars;
    private Map<String, String> headers;
    private String[] paramTypes;
    private Map<String, String> encryptParams;

    private List<PowerCaseDO> cases; // only set in CaseRunner

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSetups() {
        return setups;
    }

    public void setSetups(List<String> setups) {
        this.setups = setups;
    }

    public List<String> getTeardowns() {
        return teardowns;
    }

    public void setTeardowns(List<String> teardowns) {
        this.teardowns = teardowns;
    }

    public Map<String, String> getVars() {
        return vars;
    }

    public void setVars(Map<String, String> vars) {
        this.vars = vars;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(String[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Map<String, String> getEncryptParams() {
        return encryptParams;
    }

    public void setEncryptParams(Map<String, String> encryptParams) {
        this.encryptParams = encryptParams;
    }

    public List<PowerCaseDO> getCases() {
        return cases;
    }

    public void setCases(List<PowerCaseDO> cases) {
        this.cases = cases;
    }

}
