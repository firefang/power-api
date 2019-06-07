package io.github.firefang.power.server.permission.horizontal.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.controller.RoleController;
import io.github.firefang.power.server.mapper.IRoleMapper;
import io.github.firefang.power.server.permission.horizontal.IHorizontalChecker;

/**
 * @author xinufo
 *
 */
@Component
public class RoleHorizontalChecker implements IHorizontalChecker {
    private IRoleMapper roleMapper;

    public RoleHorizontalChecker(IRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public String type() {
        return IPowerConstants.PERM_TYPE_ROLE;
    }

    @Override
    public boolean check(Map<String, Object> params, Map<String, Object> extra) {
        Integer roleId = (Integer) params.get(RoleController.PERM_PARAM_KEY);
        // 非超级管理员不能修改超级管理员角色
        return !roleMapper.isSuperAdmin(Collections.singleton(roleId));
    }

}
