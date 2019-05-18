package io.github.firefang.power.engine.step.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.BaseEntityInfo;
import io.github.firefang.power.engine.util.Pair;

/**
 * @author xinufo
 *
 */
public abstract class BaseExpressionStep extends BaseStagedStep {

    /**
     * 根据表达式信息记录依赖的案例Key
     * 
     * @param entity
     * @param infos
     */
    protected void setDependOnCases(BaseEntityInfo entity, Collection<ExpressionInfo> infos) {
        Set<String> dependOnCases = entity.getDependOnCases();
        for (ExpressionInfo ei : infos) {
            Set<String> dependedCaseKeys = ei.getDependedCaseKeys();
            if (!dependedCaseKeys.isEmpty()) {
                dependOnCases.addAll(dependedCaseKeys);
            }
        }
    }

    /**
     * 聚合变量
     * 
     * @param parentVars
     * @return
     */
    @SafeVarargs
    protected final Map<String, Object> collectParentVars(Map<String, Object>... parentVars) {
        int size = 0;
        for (Map<String, Object> m : parentVars) {
            size += m.size();
        }
        Map<String, Object> map = new HashMap<>(CollectionUtil.mapSize(size));
        for (Map<String, Object> m : parentVars) {
            map.putAll(m);
        }
        return map;
    }

    /**
     * 聚合所有变量
     * 
     * @param cxt
     * @return
     */
    protected Map<String, Object> collectAllVars(StepContext cxt) {
        Map<String, Object> pvs = cxt.getProjectInfo().getResolvedVars();
        Map<String, Object> avs = cxt.getApiInfo().getResolvedVars();
        Map<String, Object> cvs = cxt.getCaseInfo().getResolvedVars();
        return collectParentVars(pvs, avs, cvs);
    }

    /**
     * 将普通表达式和依赖了案例的表达式分开
     * 
     * @param exps
     * @return
     */
    protected Pair<Map<String, String>, Map<String, ExpressionInfo>> divideExpressions(
            Map<String, ExpressionInfo> exps) {
        Map<String, String> toResolveMap = new HashMap<>(CollectionUtil.mapSize(exps.size()));
        Map<String, ExpressionInfo> deplayedMap = new HashMap<>(CollectionUtil.mapSize(exps.size()));
        for (Map.Entry<String, ExpressionInfo> e : exps.entrySet()) {
            String name = e.getKey();
            ExpressionInfo ei = e.getValue();
            if (ei.getDependedCaseKeys().isEmpty()) {
                toResolveMap.put(name, ei.getExpression());
            } else {
                deplayedMap.put(name, ei);
            }
        }
        return new Pair<>(toResolveMap, deplayedMap);
    }

    /**
     * 将普通表达式和依赖了案例的表达式分开
     * 
     * @param exps
     * @return
     */
    protected Pair<List<String>, List<ExpressionInfo>> divideExpressions(List<ExpressionInfo> exps) {
        List<String> toResolveList = new LinkedList<>();
        List<ExpressionInfo> deplayedList = new LinkedList<>();
        for (ExpressionInfo ei : exps) {
            if (ei.getDependedCaseKeys().isEmpty()) {
                toResolveList.add(ei.getExpression());
            } else {
                deplayedList.add(ei);
            }
        }
        return new Pair<>(toResolveList, deplayedList);
    }

    /**
     * 类型转换
     * 
     * @param exps
     * @return
     */
    protected Map<String, String> translate(Map<String, ExpressionInfo> exps) {
        return exps.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getExpression()));
    }

}
