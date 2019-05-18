package io.github.firefang.power.engine.exception;

/**
 * Thrown when the content type of request is not supported
 * 
 * @author xinufo
 *
 */
public class UnsupportedContentTypeException extends RuntimeException {
    private static final long serialVersionUID = -6600849780803702579L;

    public UnsupportedContentTypeException(String type) {
        super("不支持的报文类型: " + type);
    }

}
