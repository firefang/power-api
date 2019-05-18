package io.github.firefang.power.engine.step;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.event.PowerEventBus;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.request.IRequestBuilder;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IResponseInfo;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.AssertionFailure;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.step.entity.ProjectInfo;

/**
 * Context of running steps
 * 
 * @author xinufo
 *
 */
public class StepContext implements Cloneable {
    private PowerEventBus eventBus;
    private IExpressionEvaluator evaluator;
    private byte stage;
    // 使用数组是为了在调用setCancelled后将所有克隆出来的对象一并取消
    private boolean[] cancelled = new boolean[1];
    private String currentStep;

    private ProjectInfo projectInfo;
    private ApiInfo apiInfo;
    private CaseInfo caseInfo;

    private IRequestClient client;
    private IRequestBuilder builder;

    private IRequestInfo request;
    private IResponseInfo response;
    private boolean verifySignFailed;
    private AssertionFailure assertionFailure;
    private long duration;

    private final Map<String, Object> share = new ConcurrentHashMap<>(CollectionUtil.MAP_DEFAULT_SIZE);

    /**
     * 记录提前执行的接口结果，以便在后面执行接口Teardown时使用
     */
    private AggregateResult apiResult;

    public StepContext(PowerEventBus eventBus, IExpressionEvaluator evaluator) {
        this.eventBus = eventBus;
        this.evaluator = evaluator;
    }

    @Override
    public StepContext clone() {
        try {
            return (StepContext) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public PowerEventBus getEventBus() {
        return eventBus;
    }

    public IExpressionEvaluator getEvaluator() {
        return evaluator;
    }

    public byte getStage() {
        return stage;
    }

    public void setStage(byte stage) {
        this.stage = stage;
    }

    public boolean isCancelled() {
        return cancelled[0];
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled[0] = cancelled;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public CaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(CaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public IRequestClient getClient() {
        return client;
    }

    public void setClient(IRequestClient client) {
        this.client = client;
    }

    public IRequestBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(IRequestBuilder builder) {
        this.builder = builder;
    }

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public AggregateResult getApiResult() {
        return apiResult;
    }

    public void setApiResult(AggregateResult apiResult) {
        this.apiResult = apiResult;
    }

    public Map<String, Object> getShare() {
        return share;
    }

}
