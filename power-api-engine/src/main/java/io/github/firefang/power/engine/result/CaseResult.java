package io.github.firefang.power.engine.result;

import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IResponseInfo;

/**
 * Execution result of case
 * 
 * @author xinufo
 *
 */
public class CaseResult extends BaseResult {
    private IRequestInfo request;
    private IResponseInfo response;
    private long duration;
    private boolean verifySignFailed;
    private AssertionFailure assertionFailure;

    public IRequestInfo getRequest() {
        return request;
    }

    public void setRequest(IRequestInfo request) {
        this.request = request;
    }

    public IResponseInfo getResponse() {
        return response;
    }

    public void setResponse(IResponseInfo response) {
        this.response = response;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isVerifySignFailed() {
        return verifySignFailed;
    }

    public void setVerifySignFailed(boolean verifySignFailed) {
        this.verifySignFailed = verifySignFailed;
    }

    public AssertionFailure getAssertionFailure() {
        return assertionFailure;
    }

    public void setAssertionFailure(AssertionFailure assertionFailure) {
        this.assertionFailure = assertionFailure;
    }

}
