package io.github.firefang.power.server.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import io.github.firefang.power.server.entity.domain.GroupDO;
import io.github.firefang.power.server.entity.domain.UserDO;

/**
 * @author xinufo
 *
 */
public interface IUserGroupMapper {

    Integer add(@Param("userIds") Set<Integer> userIds, @Param("groupId") Integer groupId);

    Integer deleteByGroupId(Integer groupId);

    Integer deleteByUserId(Integer userId);

    List<UserDO> findUsersByGroupId(Integer groupId);

    List<GroupDO> findGroupsByUserId(Integer userId);

    Boolean isUserInGroup(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

}
