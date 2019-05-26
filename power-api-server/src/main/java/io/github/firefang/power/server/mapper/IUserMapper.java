package io.github.firefang.power.server.mapper;

import io.github.firefang.power.page.IPageableMapper;
import io.github.firefang.power.server.entity.domain.UserDO;

/**
 * 联合查询 auth 和 info 表
 * 
 * @author xinufo
 *
 */
public interface IUserMapper extends IPageableMapper<UserDO, Integer> {

}
