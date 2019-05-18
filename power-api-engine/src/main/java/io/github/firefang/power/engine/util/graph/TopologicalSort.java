package io.github.firefang.power.engine.util.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.exception.CircularDependencyException;

/**
 * Utility class of topological sorting
 * 
 * @author xinufo
 *
 */
public abstract class TopologicalSort {

    public static <T> List<T> sort(Collection<Vertex<T>> graph) {
        return sort(graph, null);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> List<R> sort(Collection<Vertex<T>> graph, Function<Vertex<T>, R> mapper) {
        if (mapper == null) {
            mapper = v -> (R) v.getData();
        }
        if (CollectionUtil.isEmpty(graph)) {
            return new ArrayList<>(0);
        }

        List<R> result = new ArrayList<>(graph.size());
        Queue<Vertex<T>> queue = new LinkedList<>();
        int count = 0;

        for (Vertex<T> v : graph) {
            if (v.getIndegree() == 0) {
                queue.add(v);
            }
        }

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.poll();
            result.add(mapper.apply(v));
            ++count;

            for (Vertex<T> child : v.getChildren()) {
                int indegree = child.getIndegree() - 1;
                child.setIndegree(indegree);
                if (indegree == 0) {
                    queue.add(child);
                }
            }

        }

        if (count != graph.size()) {
            throw new CircularDependencyException();
        }
        return result;
    }

}
