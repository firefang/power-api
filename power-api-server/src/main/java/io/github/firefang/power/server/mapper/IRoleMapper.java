package io.github.firefang.power.server.mapper;

import java.util.List;
import java.util.Set;

import io.github.firefang.power.mapper.IBaseMapper;
import io.github.firefang.power.server.entity.domain.RoleDO;

/**
 * @author xinufo
 *
 */
public interface IRoleMapper extends IBaseMapper<RoleDO, Integer>, INamedEntityMapper<RoleDO, Integer> {

    Boolean isSuperAdmin(Set<Integer> ids);

    Integer countByIds(Set<Integer> roleIds);

    List<RoleDO> findAll();

}
