package io.github.firefang.power.engine.exception;

/**
 * Thrown when there is a circular dependency, usually it is that try to run a
 * case in a expression which is already in process
 * 
 * @author xinufo
 *
 */
public class CircularDependencyException extends RuntimeException {
    private static final long serialVersionUID = 3725978479368017438L;

    public CircularDependencyException() {
        super("环形依赖");
    }

    public CircularDependencyException(String message) {
        super("环形依赖, " + message);
    }

}
