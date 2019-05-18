package io.github.firefang.power.engine.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.IEntityFinder;
import io.github.firefang.power.engine.entity.BaseEntity;
import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.event.PowerEvent;
import io.github.firefang.power.engine.exception.DependencyNotExistException;
import io.github.firefang.power.engine.result.AggregateResult;
import io.github.firefang.power.engine.result.BaseResult;
import io.github.firefang.power.engine.result.CaseResult;
import io.github.firefang.power.engine.result.FailureInfo;
import io.github.firefang.power.engine.step.IStepChain;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.BaseEntityInfo;

/**
 * @author xinufo
 *
 */
public abstract class BaseTask<T, E extends BaseEntity> extends RecursiveTask<T> {
    private static final long serialVersionUID = 1L;

    protected static final String MSG_START = "开始处理%s";
    protected static final String MSG_FINISH = "处理%s完成";
    protected static final String MSG_FAIL = "处理%s失败";

    protected TaskContext taskContext;
    protected StepContext stepContext;
    protected E entity;
    protected IStepChain chain;

    public BaseTask(TaskContext taskContext, StepContext stepContext, E entity) {
        this.taskContext = taskContext;
        this.stepContext = stepContext;
        this.entity = entity;
        chain = taskContext.getChain();
    }

    public E getEntity() {
        return entity;
    }

    /**
     * 根据Key查找案例并按照接口ID进行聚合
     * 
     * @param dependOnCaseKeys
     * @return
     * @throws DependencyNotExistException 当根据Key找不到对应案例时抛出异常
     */
    protected Map<Integer, List<PowerCaseDO>> getDependOnApis(Set<String> dependOnCaseKeys)
            throws DependencyNotExistException {
        IEntityFinder finder = taskContext.getFinder();
        return dependOnCaseKeys.stream().map(key -> {
            PowerCaseDO c = finder.getCaseByKey(key);
            if (c == null) {
                throw new DependencyNotExistException("案例Key: " + key);
            }
            return c;
        }).collect(Collectors.groupingBy(PowerCaseDO::getApiId)); // 按相同接口聚合案例，防止接口多次执行;
    }

    /**
     * 执行依赖案例所在的接口，执行结果放入参数传入的Map中
     * 
     * @param apiMap 接口列表
     * @param caseResults 用于存放结果的Map
     */
    protected void runDependOnApis(Map<Integer, List<PowerCaseDO>> apiMap, Map<String, BaseResult> caseResults) {
        IEntityFinder finder = taskContext.getFinder();
        // @formatter:off
        invokeAll(
            apiMap.entrySet().stream().map(e -> {
                PowerApiDO api = finder.getApiById(e.getKey());
                api.setCases(e.getValue());
                ApiTask at = new ApiTask(taskContext, stepContext.clone(), api, true);
                return at;
            }).collect(Collectors.toList())
        ).forEach(t -> {
            AggregateResult r = t.join();
            List<PowerCaseDO> cs = apiMap.get(t.getEntity().getId());
            for (PowerCaseDO c : cs) {
                caseResults.put(c.getKey(), r);
            }
        });
        // @formatter:on
    }

    /**
     * 执行项目中依赖的案例并在执行完成后清空列表
     * 
     * @param info
     * @param consumer
     */
    protected void runDependOnCases(BaseEntityInfo info, Consumer<Map<String, BaseResult>> consumer) {
        Set<String> dependOnCaseKeys = info.getDependOnCases();
        if (!dependOnCaseKeys.isEmpty()) {
            Map<String, BaseResult> caseResults = new HashMap<>(CollectionUtil.mapSize(dependOnCaseKeys.size()));
            Map<Integer, List<PowerCaseDO>> apiMap = getDependOnApis(dependOnCaseKeys);
            runDependOnApis(apiMap, caseResults);
            consumer.accept(caseResults);
            info.getDependOnCases().clear();
        }
    }

    protected void postMessage(Object source, String msg, Object... args) {
        stepContext.getEventBus().post(new PowerEvent(source, msg, args));
    }

    protected void postMessage(Object source, byte level, String msg, Object... args) {
        stepContext.getEventBus().post(new PowerEvent(source, level, msg, args));
    }

    protected AggregateResult failPreProcess(Exception e, BaseEntity entity) {
        AggregateResult result = new AggregateResult();
        String step = getTypeStr() + "预处理";
        setResultFail(e, entity, result, step);
        return result;
    }

    protected CaseResult failCasePreProcess(Exception e, BaseEntity entity) {
        CaseResult result = new CaseResult();
        setResultFail(e, entity, result, "案例预处理");
        return result;
    }

    protected String msg(String format) {
        return String.format(format, getTypeStr());
    }

    private void setResultFail(Exception e, BaseEntity entity, BaseResult result, String step) {
        result.setSuccess(false);
        result.setEntityId(entity.getId());
        result.setEntityName(entity.getName());
        FailureInfo fi = new FailureInfo(e, step);
        result.setFailureInfo(fi);
    }

    private String getTypeStr() {
        if (this instanceof ProjectTask) {
            return "项目";
        }
        if (this instanceof ApiTask) {
            return "接口";
        }
        return "案例";
    }

    protected static class DependOnCaseNotAllowedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public DependOnCaseNotAllowedException() {
            super("被依赖的案例中不允许再依赖案例");
        }

    }

}
