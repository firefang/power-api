package io.github.firefang.power.engine.result;

import java.util.List;

/**
 * Execution result of project and api
 * 
 * @author xinufo
 */
public class AggregateResult extends BaseResult {
    private List<? extends BaseResult> children;

    public List<? extends BaseResult> getChildren() {
        return children;
    }

    public void setChildren(List<? extends BaseResult> children) {
        this.children = children;
    }

}
