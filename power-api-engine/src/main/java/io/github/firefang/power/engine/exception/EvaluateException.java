package io.github.firefang.power.engine.exception;

/**
 * Thrown when evaluating a expression failed
 * 
 * @author xinufo
 *
 */
public class EvaluateException extends RuntimeException {
    private static final long serialVersionUID = 474702246595133915L;
    private String expression;

    public EvaluateException(String expression) {
        this.expression = expression;
    }

    public EvaluateException(String expression, Throwable cause) {
        super(cause);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

}
