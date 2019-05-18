package io.github.firefang.power.engine.request.dubbo;

import io.github.firefang.power.engine.request.IRequestInfo;

/**
 * Dubbo request information
 * 
 * @author xinufo
 *
 */
public class DubboRequestInfo implements IRequestInfo {
    private String regisy;
    private String className;
    private String method;
    private String[] types;
    private Object[] params;

    public String getRegisy() {
        return regisy;
    }

    public void setRegisy(String regisy) {
        this.regisy = regisy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
