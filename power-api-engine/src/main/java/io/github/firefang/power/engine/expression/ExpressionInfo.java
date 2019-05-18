package io.github.firefang.power.engine.expression;

import java.util.Set;

/**
 * Information of expression
 * 
 * @author xinufo
 *
 */
public class ExpressionInfo {
    private String expression;
    private Set<String> dependencies;
    private Set<String> dependedCaseKeys;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<String> dependencies) {
        this.dependencies = dependencies;
    }

    public Set<String> getDependedCaseKeys() {
        return dependedCaseKeys;
    }

    public void setDependedCaseKeys(Set<String> dependedCaseKeys) {
        this.dependedCaseKeys = dependedCaseKeys;
    }

}
