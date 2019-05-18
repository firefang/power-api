package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class HeaderDO {
    private Integer id;
    private Integer parentId;
    private Character parentType;
    private String key;
    private String value;
}
