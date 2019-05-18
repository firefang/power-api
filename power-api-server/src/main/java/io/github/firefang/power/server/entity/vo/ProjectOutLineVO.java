package io.github.firefang.power.server.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@Builder
public class ProjectOutLineVO {
    private Integer id;
    private String name;
    private String remark;
}
