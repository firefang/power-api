package io.github.firefang.power.engine.step.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.expression.ExpressionInfo;
import io.github.firefang.power.engine.expression.IExpressionEvaluator;

/**
 * @author xinufo
 *
 */
public abstract class BaseEntityInfo {
    /**
     * 记录实体类中所依赖的案例
     */
    private Set<String> dependOnCases = new LinkedHashSet<>(CollectionUtil.MAP_DEFAULT_SIZE);

    public BaseEntityInfo(IExpressionEvaluator evaluator) throws Exception {
        // 强制子类构造方法带参数并抛出异常
    }

    protected List<ExpressionInfo> processList(List<String> list, IExpressionEvaluator evaluator) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(i -> evaluator.preprocess(i)).collect(Collectors.toList());
    }

    protected Map<String, ExpressionInfo> processMap(Map<String, String> map, IExpressionEvaluator evaluator) {
        if (CollectionUtil.isEmpty(map)) {
            return Collections.emptyMap();
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> evaluator.preprocess(e.getValue())));
    }

    public Set<String> getDependOnCases() {
        return dependOnCases;
    }

}
