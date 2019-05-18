package io.github.firefang.power.engine.step.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.exception.DependencyNotExistException;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;
import io.github.firefang.power.engine.step.StepContext;
import io.github.firefang.power.engine.step.entity.ApiInfo;
import io.github.firefang.power.engine.step.entity.CaseInfo;
import io.github.firefang.power.engine.step.entity.ProjectInfo;
import io.github.firefang.power.engine.util.Pair;
import io.github.firefang.power.engine.util.graph.TopologicalSort;
import io.github.firefang.power.engine.util.graph.Vertex;

/**
 * Step of resolving variables
 * 
 * @author xinufo
 *
 */
public class ResolveVarsStep extends BaseExpressionStep {

    @Override
    public String name() {
        return "解析变量";
    }

    @Override
    protected void handleProjectStartPlain(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        Map<String, ExpressionInfo> vars = info.getVars();
        Pair<Map<String, Object>, Map<String, ExpressionInfo>> result = resolvePlainVars(vars, null,
                cxt.getEvaluator());
        Map<String, Object> resolved = result.getFirst();
        // 重新设置项目变量
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setVars(deplayed);
        // 记录依赖案例
        setDependOnCases(info, deplayed.values());
        info.setResolvedVars(resolved);
    }

    @Override
    protected void handleProjectStart(StepContext cxt) {
        ProjectInfo info = cxt.getProjectInfo();
        Map<String, String> toResolveVars = translate(info.getVars());
        Map<String, Object> resolvedVars = cxt.getEvaluator().evaluateVariables(toResolveVars, info.getResolvedVars());
        info.getResolvedVars().putAll(resolvedVars);
        info.setVars(null); // Vars已经解析完成，释放掉节约内存空间
    }

    @Override
    protected void handleApiStartPlain(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        Map<String, ExpressionInfo> vars = info.getVars();
        Pair<Map<String, Object>, Map<String, ExpressionInfo>> result = resolvePlainVars(vars,
                cxt.getProjectInfo().getResolvedVars(), cxt.getEvaluator());
        Map<String, Object> resolved = result.getFirst();
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setVars(deplayed);
        setDependOnCases(info, deplayed.values());
        info.setResolvedVars(resolved);
    }

    @Override
    protected void handleApiStart(StepContext cxt) {
        ApiInfo info = cxt.getApiInfo();
        Map<String, Object> vars = collectParentVars(cxt.getProjectInfo().getResolvedVars(), info.getResolvedVars());
        Map<String, String> toResolve = translate(info.getVars());
        Map<String, Object> resolved = cxt.getEvaluator().evaluateVariables(toResolve, vars);
        info.getResolvedVars().putAll(resolved);
        info.setVars(null);
    }

    @Override
    protected void handleCaseStartPlain(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        Map<String, ExpressionInfo> vars = info.getVars();
        Map<String, Object> parentVars = collectParentVars(cxt.getProjectInfo().getResolvedVars(),
                cxt.getApiInfo().getResolvedVars());
        Pair<Map<String, Object>, Map<String, ExpressionInfo>> result = resolvePlainVars(vars, parentVars,
                cxt.getEvaluator());
        Map<String, Object> resolved = result.getFirst();
        Map<String, ExpressionInfo> deplayed = result.getSecond();
        info.setVars(deplayed);
        setDependOnCases(info, deplayed.values());
        info.setResolvedVars(resolved);

    }

    @Override
    protected void handleCaseStart(StepContext cxt) {
        CaseInfo info = cxt.getCaseInfo();
        Map<String, Object> parentVars = collectAllVars(cxt);
        Map<String, String> toResolve = translate(info.getVars());
        Map<String, Object> resolved = cxt.getEvaluator().evaluateVariables(toResolve, parentVars);
        info.getResolvedVars().putAll(resolved);
        info.setVars(null);
    }

    /**
     * 解析普通（不依赖案例）变量
     * 
     * @param vars 要解析的变量列表
     * @param parentVars 已经解析的变量
     * @param evaluator 解析器
     * @return Pair: 1.解析好的普通（不依赖案例）变量，2.依赖案例的变量列表
     */
    private Pair<Map<String, Object>, Map<String, ExpressionInfo>> resolvePlainVars(Map<String, ExpressionInfo> vars,
            Map<String, Object> parentVars, IExpressionEvaluator evaluator) {
        Collection<Vertex<Pair<String, ExpressionInfo>>> graph = buildGraph(vars, parentVars);
        List<Vertex<Pair<String, ExpressionInfo>>> sortedVars = TopologicalSort.sort(graph, v -> v);
        Set<String> delayedVars = getDeplayedVars(sortedVars);
        // use LinkedHashMap to retain order
        Map<String, ExpressionInfo> delayedMap = new LinkedHashMap<>(CollectionUtil.mapSize(vars.size()));
        Map<String, String> toResolveMap = new LinkedHashMap<>(CollectionUtil.mapSize(vars.size()));
        for (Vertex<Pair<String, ExpressionInfo>> sv : sortedVars) {
            Pair<String, ExpressionInfo> pair = sv.getData();
            String key = pair.getFirst();
            ExpressionInfo info = pair.getSecond();
            if (delayedVars.contains(key)) {
                delayedMap.put(key, info);
            } else {
                toResolveMap.put(key, info.getExpression());
            }
        }
        Map<String, Object> resolvedMap = new HashMap<>(evaluator.evaluateVariables(toResolveMap, parentVars));
        return new Pair<>(resolvedMap, delayedMap);
    }

    /**
     * 创建图，用于拓扑排序
     * 
     * @param vars
     * @param parentVars
     * @return
     */
    private Collection<Vertex<Pair<String, ExpressionInfo>>> buildGraph(Map<String, ExpressionInfo> vars,
            Map<String, Object> parentVars) {
        Map<String, Vertex<Pair<String, ExpressionInfo>>> vertexMap = new HashMap<>(
                CollectionUtil.mapSize(vars.size()));
        for (Map.Entry<String, ExpressionInfo> e : vars.entrySet()) {
            String name = e.getKey();
            ExpressionInfo ei = e.getValue();
            Vertex<Pair<String, ExpressionInfo>> v = vertexMap.computeIfAbsent(name, n -> {
                Vertex<Pair<String, ExpressionInfo>> vertex = new Vertex<>(new Pair<>(n, ei));
                Set<String> dependencies = processDependencies(ei.getDependencies(), parentVars);
                ei.setDependencies(dependencies);
                vertex.setIndegree(dependencies.size());
                return vertex;
            });
            for (String d : ei.getDependencies()) {
                Vertex<Pair<String, ExpressionInfo>> dependOn = vertexMap.computeIfAbsent(d, n -> {
                    ExpressionInfo info = vars.get(n);
                    if (info == null) {
                        throw new DependencyNotExistException("变量名: " + d);
                    }
                    Vertex<Pair<String, ExpressionInfo>> vertex = new Vertex<>(new Pair<>(n, info));
                    Set<String> dependencies = processDependencies(info.getDependencies(), parentVars);
                    info.setDependencies(dependencies);
                    vertex.setIndegree(dependencies.size());
                    return vertex;
                });
                dependOn.addChildren(v);
            }
        }
        return vertexMap.values();
    }

    /**
     * 处理依赖列表，删除列表中对父级变量的依赖
     * 
     * @param dependencies
     * @param parentVars
     * @return
     */
    private Set<String> processDependencies(Set<String> dependencies, Map<String, Object> parentVars) {
        if (parentVars == null) {
            return dependencies;
        }

        Set<String> keys = parentVars.keySet();
        Set<String> ret = new HashSet<>(CollectionUtil.mapSize(dependencies.size()));

        for (String d : dependencies) {
            if (!keys.contains(d)) {
                ret.add(d);
            }
        }
        return ret;
    }

    /**
     * 获取依赖了案例的变量
     * 
     * @param vars
     * @return
     */
    private Set<String> getDeplayedVars(Collection<Vertex<Pair<String, ExpressionInfo>>> vars) {
        Set<String> delayedVars = new HashSet<>(CollectionUtil.mapSize(vars.size()));
        for (Vertex<Pair<String, ExpressionInfo>> v : vars) {
            ExpressionInfo info = v.getData().getSecond();
            if (!info.getDependedCaseKeys().isEmpty()) {
                delayedVars.add(v.getData().getFirst());
                getDeplayedChildren(v.getChildren(), delayedVars);
            }
        }
        return delayedVars;
    }

    private void getDeplayedChildren(Collection<Vertex<Pair<String, ExpressionInfo>>> vars, Set<String> delayedVars) {
        for (Vertex<Pair<String, ExpressionInfo>> v : vars) {
            delayedVars.add(v.getData().getFirst());
            getDeplayedChildren(v.getChildren(), delayedVars);
        }
    }

}
