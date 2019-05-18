package io.github.firefang.power.engine.exception;

/**
 * Thrown when the request type is not supported
 * 
 * @author xinufo
 *
 */
public class UnsupportedRequestTypeException extends RuntimeException {
    private static final long serialVersionUID = 3347056933488194487L;

    public UnsupportedRequestTypeException(String type) {
        super("不支持的接口类型: " + type);
    }

}
