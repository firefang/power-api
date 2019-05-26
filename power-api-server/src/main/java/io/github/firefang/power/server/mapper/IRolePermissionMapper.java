package io.github.firefang.power.server.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

/**
 * @author xinufo
 *
 */
public interface IRolePermissionMapper {

    Integer countPermission(@Param("roleIds") Set<Integer> roleIds, @Param("permissionName") String permissionName);

}
