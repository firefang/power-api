package io.github.firefang.power.engine.request.dubbo;

import io.github.firefang.power.engine.request.IResponseInfo;

/**
 * Dubbo response information
 * 
 * @author xinufo
 *
 */
public class DubboResponseInfo implements IResponseInfo {
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
