package io.github.firefang.power.server.permission;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.permission.IPermissionChecker;
import io.github.firefang.power.permission.UserInfo;
import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.auth.TokenHelper;
import io.github.firefang.power.server.cache.TokenCache;
import io.github.firefang.power.server.mapper.IRoleMapper;
import io.github.firefang.power.server.mapper.IRolePermissionMapper;
import io.github.firefang.power.server.mapper.IUserRoleMapper;
import io.github.firefang.power.server.permission.horizontal.IHorizontalChecker;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xinufo
 *
 */
@Slf4j
@Service
public class PermissionCheckerService implements IPermissionChecker, ApplicationContextAware {
    private IRoleMapper roleMapper;
    private IUserRoleMapper userRoleMapper;
    private IRolePermissionMapper rolePermissionMapper;

    private Map<String, IHorizontalChecker> horizontalCheckers;

    public PermissionCheckerService(IRoleMapper roleMapper, IUserRoleMapper userRoleMapper,
            IRolePermissionMapper rolePermissionMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public UserInfo getUserInfoFromRequest(HttpServletRequest request) {
        String token = request.getHeader(IPowerConstants.TOKEN_KEY);
        Integer userId = TokenCache.getUserId(token);
        if (userId == null) {
            userId = TokenHelper.decodeUserId(token);
        }
        Set<Integer> roleIds = userRoleMapper.findIdsByUserId(userId);
        UserInfo info = new UserInfo();
        info.setUserId(userId);
        info.setRoleId(roleIds);
        return info;
    }

    @Override
    public boolean horizontalCheck(String permission, UserInfo info, Map<String, Object> params,
            Map<String, Object> extra) {
        @SuppressWarnings("unchecked")
        Set<Integer> roleIds = (Set<Integer>) info.getRoleId();
        if (CollectionUtil.isEmpty(roleIds)) {
            return false;
        }
        Boolean superAdmin = roleMapper.isSuperAdmin(roleIds);
        if (superAdmin != null && superAdmin) {
            return true;
        }
        String type = (String) extra.get(IPowerConstants.PERM_TYPE_KEY);
        IHorizontalChecker checker = horizontalCheckers.get(type);
        if (checker == null) {
            return false;
        }
        return checker.check(params, extra);
    }

    @Override
    public boolean verticalCheck(String permission, UserInfo info, Map<String, Object> params,
            Map<String, Object> extra) {
        @SuppressWarnings("unchecked")
        Set<Integer> roleIds = (Set<Integer>) info.getRoleId();
        if (CollectionUtil.isEmpty(roleIds)) {
            return false;
        }
        Boolean superAdmin = roleMapper.isSuperAdmin(roleIds);
        if (superAdmin != null && superAdmin) {
            return true;
        }
        return rolePermissionMapper.countPermission(roleIds, permission) > 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IHorizontalChecker> map = applicationContext.getBeansOfType(IHorizontalChecker.class);
        horizontalCheckers = map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getValue().type(), e -> e.getValue()));
        if (log.isDebugEnabled()) {
            log.debug(horizontalCheckers.keySet().toString());
        }
    }

}
