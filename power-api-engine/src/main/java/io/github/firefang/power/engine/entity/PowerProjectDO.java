package io.github.firefang.power.engine.entity;

import java.util.List;
import java.util.Map;

/**
 * Entity of project
 * 
 * @author xinufo
 */
public class PowerProjectDO extends BaseEntity {
    private String basePath;

    private List<String> setups;
    private List<String> teardowns;
    private Map<String, String> vars;
    private String encryptClass;
    private Map<String, String> headers;

    private List<PowerApiDO> apis; // only set in CaseRunner

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
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

    public String getEncryptClass() {
        return encryptClass;
    }

    public void setEncryptClass(String encryptClass) {
        this.encryptClass = encryptClass;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public List<PowerApiDO> getApis() {
        return apis;
    }

    public void setApis(List<PowerApiDO> apis) {
        this.apis = apis;
    }

}
