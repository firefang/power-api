package io.github.firefang.power.server.entity.domain;

import java.util.Map;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class RequestParamDO {
    private Integer id;
    private Integer caseId;
    private Map<String, Object> param;
}
