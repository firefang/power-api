package io.github.firefang.power.server.entity.domain;

import io.github.firefang.power.permission.serialize.PermissionDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xinufo
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermDO extends PermissionDO {
    private Integer id;
}
