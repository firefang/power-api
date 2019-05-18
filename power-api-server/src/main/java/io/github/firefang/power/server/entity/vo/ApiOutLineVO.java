package io.github.firefang.power.server.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@Builder
public class ApiOutLineVO {
    private Integer id;
    private Integer projectId;
    private String name;
    private String type;
}
