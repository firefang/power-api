package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class VarDO {
    private Integer id;
    private Integer parentId;
    private Character parentType;
    private String name;
    private String expression;
}
