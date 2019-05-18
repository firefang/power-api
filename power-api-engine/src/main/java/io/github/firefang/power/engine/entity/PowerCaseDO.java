package io.github.firefang.power.engine.entity;

import java.util.List;
import java.util.Map;

/**
 * Entity of case
 * 
 * @author xinufo
 */
public class PowerCaseDO extends BaseEntity {
    private Integer apiId;
    private String key;

    private List<String> setups;
    private List<String> teardowns;
    private Map<String, String> vars;
    private Map<String, String> headers;
    // {type: "form|body|array", content: Map<String, String>|String|String[]}
    private Map<String, Object> requestParams;
    private List<String> assertions;

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }

    public List<String> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<String> assertions) {
        this.assertions = assertions;
    }

}
