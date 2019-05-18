package io.github.firefang.power.engine.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.IEntityFinder;
import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ProjectInfo;

/**
 * @author xinufo
 *
 */
public class ProjectTask extends BaseTask<AggregateResult, PowerProjectDO> {
    private static final long serialVersionUID = 1L;
    private ProjectInfo info;

    public ProjectTask(TaskContext taskContext, StepContext stepContext, PowerProjectDO entity) {
        super(taskContext, stepContext, entity);
    }

    @Override
    protected AggregateResult compute() {
        // 项目预处理
        try {
            info = chain.preprocessProject(stepContext, entity);
            stepContext.setProjectInfo(info);
        } catch (Exception e) {
            // 传递true用于判断执行是否结束
            postMessage(this, msg(MSG_FAIL), entity, true);
            return failPreProcess(e, entity);
        }

        // start
        postMessage(this, msg(MSG_START), entity);
        AggregateResult result = start();
        if (!result.isSuccess()) {
            postMessage(this, msg(MSG_FAIL), entity, true);
            return result;
        }

        // children
        List<AggregateResult> childrenResults = forkJoinChildrenTasks();

        // 执行被依赖案例接口的Teardown
        runLeftApiTeardowns();

        // end
        result = end();
        result.setChildren(childrenResults);
        if (!result.isSuccess()) {
            postMessage(this, msg(MSG_FAIL), entity, true);
        } else {
            postMessage(this, msg(MSG_FINISH), entity, true);
        }
        return result;
    }

    private AggregateResult start() {
        // 跳过依赖案例解析
        AggregateResult result = chain.projectStart(stepContext, true);
        if (result.isSuccess()) {
            // 执行依赖的案例
            runDependOnCases(info, results -> info.getResolvedVars().put(EngineConstants.KEY_CASE_RESULTS, results));
            // 重新解析
            result = chain.projectStart(stepContext, false);
        }
        return result;
    }

    private List<AggregateResult> forkJoinChildrenTasks() {
        List<PowerApiDO> children = taskContext.getFinder().getApisByProjectId(entity.getId());
        List<AggregateResult> childrenResults = new ArrayList<>(children.size());
        // 因为多线程的缘故，此处StepContext采用克隆模式，防止结果相互覆盖
        // @formatter:off
        invokeAll(children.stream()
                .map(child -> new ApiTask(taskContext, stepContext.clone(), child, false))
                .collect(Collectors.toList()))
        .forEach(t -> childrenResults.add(t.join()));
        // @formatter:on
        return childrenResults;
    }

    private AggregateResult end() {
        AggregateResult result = chain.projectEnd(stepContext, true);
        if (result.isSuccess()) {
            runDependOnCases(info, results -> info.getResolvedVars().put(EngineConstants.KEY_CASE_RESULTS, results));
            result = chain.projectEnd(stepContext, false);
        }
        return result;
    }

    /**
     * 执行被依赖案例接口的Teardown
     */
    private void runLeftApiTeardowns() {
        Map<Integer, StepContext> unTeardownApis = taskContext.getUnTeardownApiIds();
        if (!unTeardownApis.isEmpty()) {
            IEntityFinder finder = taskContext.getFinder();
            List<ApiTask> ats = new ArrayList<>(unTeardownApis.size());
            for (Map.Entry<Integer, StepContext> e : unTeardownApis.entrySet()) {
                ats.add(new ApiTask(taskContext, e.getValue(), finder.getApiById(e.getKey()), false));
            }
            Collection<ApiTask> ts = invokeAll(ats);
            for (ApiTask t : ts) {
                AggregateResult result = t.join();
                if (!result.isSuccess()) {
                    // 执行失败仅进行通知，不影响整体结果
                    // 因为此处的接口仅用于执行被依赖的案例，案例已经执行成功，Teardown失败并不会造成本质上的影响
                    postMessage(this, msg(MSG_FAIL), result);
                }
            }
        }
    }

}
