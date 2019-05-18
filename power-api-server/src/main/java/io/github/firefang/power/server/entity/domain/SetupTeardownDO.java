package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class SetupTeardownDO {
    private Integer id;
    private Integer parentId;
    private Character parentType;
    private String expression;
    private boolean teardown;
}
