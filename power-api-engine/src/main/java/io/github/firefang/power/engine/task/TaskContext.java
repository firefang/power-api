package io.github.firefang.power.engine.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.IEntityFinder;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.StepContext;

/**
 * @author xinufo
 *
 */
public class TaskContext {
    /**
     * 用以记录未执行Teardown的接口，此中的记录会在接口正常执行后被删除，若记录到项目结束后仍存在，则会在项目结束前统一执行Teardown
     */
    private final Map<Integer, StepContext> unTeardownApis = new ConcurrentHashMap<>(CollectionUtil.MAP_DEFAULT_SIZE);
    private IEntityFinder finder;
    private IStepChain chain;

    public TaskContext(IEntityFinder finder, IStepChain chain) {
        this.finder = finder;
        this.chain = chain;
    }

    public IEntityFinder getFinder() {
        return finder;
    }

    public IStepChain getChain() {
        return chain;
    }

    public Map<Integer, StepContext> getUnTeardownApiIds() {
        return unTeardownApis;
    }

}
