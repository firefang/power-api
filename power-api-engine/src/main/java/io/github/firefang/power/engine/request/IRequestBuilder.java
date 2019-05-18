package io.github.firefang.power.engine.request;

import java.util.Map;

/**
 * Builder to build a BaseRequestInfo
 * 
 * @author xinufo
 *
 */
public interface IRequestBuilder {

    /**
     * Set request target
     * 
     * @param basePath
     * @param target
     */
    void setTarget(String basePath, String target);

    /**
     * Set request method
     * 
     * @param method
     */
    void setMethod(String method);

    /**
     * Set request headers
     * 
     * @param headers
     */
    void setHeaders(Map<String, String> headers);

    /**
     * Set request parameter types
     * 
     * @param types
     */
    void setRequestParamTypes(String[] types);

    /**
     * Set request parameters
     * 
     * @param params
     */
    void setRequestParams(Object[] params);

    /**
     * Generate the request object
     * 
     * @return
     */
    IRequestInfo build();

}
