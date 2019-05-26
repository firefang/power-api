package io.github.firefang.power.server.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import io.github.firefang.power.server.entity.domain.RoleDO;

/**
 * @author xinufo
 *
 */
public interface IUserRoleMapper {

    Integer add(@Param("userId") Integer userId, @Param("roleIds") Set<Integer> roleIds);

    Integer deleteByUserId(Integer userId);

    Integer deleteByRoleId(Integer roleId);

    List<RoleDO> findByUserId(Integer userId);

    Set<Integer> findIdsByUserId(Integer userId);

    Integer countByRoleId(Integer roleId);

}
