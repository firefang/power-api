package io.github.firefang.power.engine;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.event.IPowerEventListener;
import io.github.firefang.power.engine.event.PowerEventBus;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.task.DelegateFuture;
import io.github.firefang.power.engine.task.ProjectTask;
import io.github.firefang.power.engine.task.TaskContext;

/**
 * Default implementation of ICaseRunner. It can NOT be reused.
 * 
 * @author xinufo
 */
public class DefaultCaseRunner implements ICaseRunner {
    private ForkJoinPool pool = new ForkJoinPool();
    private IEntityFinder finder;
    private IStepChain chain;
    private IExpressionEvaluator evaluator;
    private PowerEventBus bus;

    @Override
    public void init(IEntityFinder finder, IStepChain chain, IExpressionEvaluator evaluator,
            List<IPowerEventListener> listeners) {
        this.finder = finder;
        this.chain = chain;
        this.evaluator = evaluator;
        this.bus = new PowerEventBus();

        if (!CollectionUtil.isEmpty(listeners)) {
            for (IPowerEventListener listener : listeners) {
                bus.registerListener(listener);
            }
        }
    }

    @Override
    public Future<AggregateResult> run(PowerProjectDO projectEntity) {
        StepContext sc = new StepContext(bus, evaluator);
        TaskContext tc = new TaskContext(finder, chain);
        Future<AggregateResult> future = pool.submit(new ProjectTask(tc, sc, projectEntity));
        return new DelegateFuture<>(future, pool, sc);
    }

    @Override
    public Future<AggregateResult> run(PowerApiDO apiEntity) {
        PowerProjectDO projectEntity = finder.getProjectById(apiEntity.getProjectId());
        List<PowerCaseDO> cases = finder.getCasesByApiId(apiEntity.getId());
        projectEntity.setApis(Arrays.asList(apiEntity));
        apiEntity.setCases(cases);
        return run(projectEntity);
    }

    @Override
    public Future<AggregateResult> run(PowerCaseDO caseEntity) {
        PowerApiDO apiEntity = finder.getApiById(caseEntity.getApiId());
        PowerProjectDO projectEntity = finder.getProjectById(apiEntity.getProjectId());
        projectEntity.setApis(Arrays.asList(apiEntity));
        apiEntity.setCases(Arrays.asList(caseEntity));
        return run(projectEntity);
    }

}
