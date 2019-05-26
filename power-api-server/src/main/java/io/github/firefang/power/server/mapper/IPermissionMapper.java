package io.github.firefang.power.server.mapper;

import java.util.List;
import java.util.Set;

import io.github.firefang.power.server.entity.domain.PermDO;

/**
 * @author xinufo
 *
 */
public interface IPermissionMapper {

    Integer addBatch(List<PermDO> entities);

    Integer deleteNameNotIn(Set<String> names);

    Integer updateBatch(List<PermDO> entities);

    Long countByIds(Set<Integer> ids);

    List<PermDO> findAll();

}
