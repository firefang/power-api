package io.github.firefang.power.engine.step;

import io.github.firefang.power.engine.result.BaseResult;

/**
 * Step of running a case
 * 
 * @author xinufo
 *
 */
public interface IStep {

    /**
     * Name of the step
     * 
     * @return
     */
    String name();

    /**
     * Process
     * 
     * @param cxt
     * @param chain
     * @return
     */
    BaseResult process(StepContext cxt, IStepChain chain);

}
