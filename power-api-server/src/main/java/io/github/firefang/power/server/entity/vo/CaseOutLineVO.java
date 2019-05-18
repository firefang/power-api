package io.github.firefang.power.server.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@Builder
public class CaseOutLineVO {
    private Integer id;
    private Integer apiId;
    private String name;
    private String key;
}
