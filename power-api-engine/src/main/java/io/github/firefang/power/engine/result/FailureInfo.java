package io.github.firefang.power.engine.result;

/**
 * Information of failure
 * 
 * @author xinufo
 *
 */
public class FailureInfo {
    private Throwable exception;
    private String failStep;

    public FailureInfo(Throwable exception, String failStep) {
        this.exception = exception;
        this.failStep = failStep;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getFailStep() {
        return failStep;
    }

    public void setFailStep(String failStep) {
        this.failStep = failStep;
    }

}
