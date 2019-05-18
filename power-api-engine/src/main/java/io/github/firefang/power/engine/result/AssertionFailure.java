package io.github.firefang.power.engine.result;

/**
 * Failure information of an assertion
 * 
 * @author xinufo
 *
 */
public class AssertionFailure {
    private final String expression;
    private final String message;
    private final Exception exception;

    public AssertionFailure(String expression, String message, Exception exception) {
        super();
        this.expression = expression;
        this.message = message;
        this.exception = exception;
    }

    public String getExpression() {
        return expression;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }

}
