package io.github.firefang.power.server.entity.form;

/**
 * @author xinufo
 *
 */
public enum NodeType {
    PROJECT('P'), API('A'), CASE('C');

    private char type;

    private NodeType(char type) {
        this.type = type;
    }

    public Character getType() {
        return type;
    }

}
