package io.github.firefang.power.engine.result;

/**
 * Base class for execution result
 * 
 * @author xinufo
 */
public abstract class BaseResult {
    private Integer entityId;
    private String entityName;
    private boolean success;
    private FailureInfo failureInfo;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FailureInfo getFailureInfo() {
        return failureInfo;
    }

    public void setFailureInfo(FailureInfo failureInfo) {
        this.failureInfo = failureInfo;
    }

}
