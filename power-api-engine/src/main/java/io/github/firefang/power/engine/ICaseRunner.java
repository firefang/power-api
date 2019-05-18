package io.github.firefang.power.engine;

import java.util.List;
import java.util.concurrent.Future;

import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.event.IPowerEventListener;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.step.IStepChain;

/**
 * Runner to execute cases
 * 
 * @author xinufo
 *
 */
public interface ICaseRunner {

    /**
     * Initialize the runner
     * 
     * @param finder
     * @param chain
     * @param evaluator
     * @param listeners
     */
    void init(IEntityFinder finder, IStepChain chain, IExpressionEvaluator evaluator,
            List<IPowerEventListener> listeners);

    /**
     * Execute cases by project
     * 
     * @param projectEntity
     * @return
     */
    Future<AggregateResult> run(PowerProjectDO projectEntity);

    /**
     * Execute cases by api
     * 
     * @param apiEntity
     * @return
     */
    Future<AggregateResult> run(PowerApiDO apiEntity);

    /**
     * Execute a single case
     * 
     * @param caseEntity
     * @return
     */
    Future<AggregateResult> run(PowerCaseDO caseEntity);

}
