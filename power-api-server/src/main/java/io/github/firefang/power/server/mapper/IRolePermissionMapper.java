package io.github.firefang.power.server.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import io.github.firefang.power.server.entity.domain.PermDO;

/**
 * @author xinufo
 *
 */
public interface IRolePermissionMapper {

    Integer add(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);

    Integer deleteByRoleId(Integer roleId);

    Integer countPermission(@Param("roleIds") Set<Integer> roleIds, @Param("permissionName") String permissionName);

    List<PermDO> findPermissionsByRoleId(Integer roleId);

}
