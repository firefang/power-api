package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class ProjectDO {
    private Integer id;
    private Integer groupId;
    private String name;
    private String remark;
    private String encryptClass;
}
