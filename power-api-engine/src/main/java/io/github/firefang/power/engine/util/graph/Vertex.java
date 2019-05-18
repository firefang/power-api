package io.github.firefang.power.engine.util.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Vertex of a graph
 * 
 * @author xinufo
 *
 * @param <T>
 */
public class Vertex<T> {
    private T data;
    private List<Vertex<T>> children = new LinkedList<>();
    private int indegree;

    public Vertex(T data) {
        this.data = data;
    }

    public void addChildren(Vertex<T> child) {
        children.add(child);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Vertex<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Vertex<T>> children) {
        this.children = children;
    }

    public int getIndegree() {
        return indegree;
    }

    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }

}
