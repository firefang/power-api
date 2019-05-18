package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class AssertionDO {
    private Integer id;
    private Integer caseId;
    private String expression;
}
