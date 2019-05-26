package io.github.firefang.power.server.mapper;

import io.github.firefang.power.mapper.IBaseMapper;
import io.github.firefang.power.server.entity.domain.UserInfoDO;

/**
 * @author xinufo
 *
 */
public interface IUserInfoMapper extends IBaseMapper<UserInfoDO, Integer> {

    UserInfoDO findByNickname(String nickname);

}
