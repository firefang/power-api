package io.github.firefang.power.engine.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.result.FailureInfo;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;

/**
 * @author xinufo
 *
 */
public class ApiTask extends BaseTask<AggregateResult, PowerApiDO> {
    private static final long serialVersionUID = 1L;
    private ApiInfo info;

    /**
     * 是否是执行依赖案例的模式，当为true时不会执行Teardown（因为接口是提前执行的，执行Teardown可能会影响后续案例），并检查案例是否存在循环依赖
     */
    private boolean runDependOnCaseMode;

    public ApiTask(TaskContext taskContext, StepContext stepContext, PowerApiDO entity, boolean runDependOnCaseMode) {
        super(taskContext, stepContext, entity);
        this.runDependOnCaseMode = runDependOnCaseMode;
    }

    @Override
    protected AggregateResult compute() {
        AggregateResult result;
        StepContext cache = getResolvedApi();
        if (cache == null) {
            try {
                info = chain.preprocessApi(stepContext, entity);
                stepContext.setApiInfo(info);
            } catch (Exception e) {
                postMessage(this, msg(MSG_FAIL), entity);
                result = failPreProcess(e, entity);
                determinRecordResult(result);
                return result;
            }

            // start
            postMessage(this, msg(MSG_START), entity);
            result = start();
            determinRecordResult(result);
            if (!result.isSuccess()) {
                postMessage(this, msg(MSG_FAIL), entity);
                return result;
            }
        } else {
            this.stepContext = cache;
            info = cache.getApiInfo();
            // 使用之前的结果
            result = cache.getApiResult();
            if (!result.isSuccess()) {
                // 之前执行失败直接返回
                return result;
            }
        }

        // children
        List<CaseResult> childrenResults = forkJoinChildrenTasks();
        result.setChildren(childrenResults);

        if (runDependOnCaseMode) {
            if (cache == null) {
                // 记录未执行Teardown的接口
                taskContext.getUnTeardownApiIds().put(entity.getId(), stepContext);
            }
        } else {
            // end
            result = end();
            result.setChildren(childrenResults);
            if (!result.isSuccess()) {
                postMessage(this, msg(MSG_FAIL), entity);
            } else {
                postMessage(this, msg(MSG_FINISH), entity);
            }
            // 正常模式下执行完Teardown后删除记录
            taskContext.getUnTeardownApiIds().remove(entity.getId());
        }
        return result;
    }

    private AggregateResult start() {
        AggregateResult result = chain.apiStart(stepContext, true);
        if (result.isSuccess()) {
            try {
                handleDependOnCases();
                result = chain.apiStart(stepContext, false);
            } catch (DependOnCaseNotAllowedException e) {
                result.setSuccess(false);
                result.setFailureInfo(new FailureInfo(e, "处理依赖案例"));
            }
        }
        return result;
    }

    private List<CaseResult> forkJoinChildrenTasks() {
        return null;
    }

    private AggregateResult end() {
        return null;
    }

    private StepContext getResolvedApi() {
        return taskContext.getUnTeardownApiIds().get(entity.getId());
    }

    private void determinRecordResult(AggregateResult result) {
        if (runDependOnCaseMode) {
            stepContext.setApiResult(result);
        }
    }

    private void handleDependOnCases() throws DependOnCaseNotAllowedException {
        Set<String> dependOnCaseKeys = info.getDependOnCases();
        if (!dependOnCaseKeys.isEmpty()) {
            if (runDependOnCaseMode) {
                // 被依赖的案例中不允许再依赖案例
                throw new DependOnCaseNotAllowedException();
            } else {
                runDependOnCases(info);
            }
            info.getDependOnCases().clear();
        }
    }

    /**
     * 处理接口中依赖的案例
     * 
     * @param info
     */
    private void runDependOnCases(ApiInfo info) {
        Set<String> dependOnCaseKeys = info.getDependOnCases();
        Map<Integer, List<PowerCaseDO>> apiMap = getDependOnApis(dependOnCaseKeys);
        Map<String, BaseResult> caseResults = new HashMap<>(CollectionUtil.mapSize(dependOnCaseKeys.size()));
        // 依赖的案例属于本接口
        List<PowerCaseDO> sameApiCases = apiMap.remove(entity.getId());
        if (!CollectionUtil.isEmpty(sameApiCases)) {
            invokeAll(sameApiCases.stream().map(c -> new CaseTask(taskContext, stepContext.clone(), c, true))
                    .collect(Collectors.toList())).forEach(t -> {
                        CaseResult r = t.join();
                        caseResults.put(t.getEntity().getKey(), r);
                    });
        }
        // 依赖的案例不属于本接口
        runDependOnApis(apiMap, caseResults);
        info.getResolvedVars().put(EngineConstants.KEY_CASE_RESULTS, caseResults);
    }

}
