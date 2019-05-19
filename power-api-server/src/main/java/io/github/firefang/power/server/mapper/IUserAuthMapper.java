package io.github.firefang.power.server.mapper;

import java.util.Set;

import io.github.firefang.power.mapper.IBaseMapper;
import io.github.firefang.power.server.entity.domain.UserAuthDO;

/**
 * @author xinufo
 *
 */
public interface IUserAuthMapper extends IBaseMapper<UserAuthDO, Integer> {

    UserAuthDO findByUsername(String username);

    Long count(Set<Integer> ids);

}
