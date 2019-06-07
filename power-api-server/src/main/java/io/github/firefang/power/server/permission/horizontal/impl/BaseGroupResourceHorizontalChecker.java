package io.github.firefang.power.server.permission.horizontal.impl;

import java.util.Map;

import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.auth.TokenHelper;
import io.github.firefang.power.server.cache.TokenCache;
import io.github.firefang.power.server.mapper.IUserGroupMapper;
import io.github.firefang.power.server.permission.horizontal.IHorizontalChecker;
import io.github.firefang.power.web.util.CurrentRequestUtil;

/**
 * @author xinufo
 *
 */
public abstract class BaseGroupResourceHorizontalChecker implements IHorizontalChecker {
    private IUserGroupMapper userGroupMapper;

    public BaseGroupResourceHorizontalChecker(IUserGroupMapper userGroupMapper) {
        this.userGroupMapper = userGroupMapper;
    }

    @Override
    public boolean check(Map<String, Object> params, Map<String, Object> extra) {
        Integer groupId = getGroupId(params, extra);
        String token = CurrentRequestUtil.getHeader(IPowerConstants.TOKEN_KEY);
        Integer userId = TokenCache.getUserId(token);
        if (userId == null) {
            userId = TokenHelper.decodeUserId(token);
        }
        return userGroupMapper.isUserInGroup(userId, groupId);
    }

    protected abstract Integer getGroupId(Map<String, Object> params, Map<String, Object> extra);

}
