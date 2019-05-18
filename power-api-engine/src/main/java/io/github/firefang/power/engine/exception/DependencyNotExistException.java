package io.github.firefang.power.engine.exception;

/**
 * Thrown when the case run in expression by key doesn't exist
 * 
 * @author xinufo
 *
 */
public class DependencyNotExistException extends RuntimeException {
    private static final long serialVersionUID = 3725978479368017438L;

    public DependencyNotExistException(String dependency) {
        super("依赖不存在, " + dependency);
    }

}
