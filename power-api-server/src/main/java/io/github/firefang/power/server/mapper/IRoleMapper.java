package io.github.firefang.power.server.mapper;

import java.util.Set;

import io.github.firefang.power.mapper.IBaseMapper;
import io.github.firefang.power.server.entity.domain.RoleDO;

/**
 * @author xinufo
 *
 */
public interface IRoleMapper extends IBaseMapper<RoleDO, Integer> {

    Boolean isSuperAdmin(Set<Integer> ids);

    Integer countByIds(Set<Integer> roleIds);

}
