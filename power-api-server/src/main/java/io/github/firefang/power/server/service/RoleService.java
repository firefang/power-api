package io.github.firefang.power.server.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.server.cache.CacheLock;
import io.github.firefang.power.server.entity.domain.PermDO;
import io.github.firefang.power.server.entity.domain.RoleDO;
import io.github.firefang.power.server.entity.form.RoleForm;
import io.github.firefang.power.server.entity.form.UpdateRolePermissionsForm;
import io.github.firefang.power.server.mapper.IPermissionMapper;
import io.github.firefang.power.server.mapper.IRoleMapper;
import io.github.firefang.power.server.mapper.IRolePermissionMapper;
import io.github.firefang.power.server.mapper.IUserRoleMapper;
import io.github.firefang.power.server.service.base.BaseService;

/**
 * @author xinufo
 *
 */
@Service
public class RoleService extends BaseService<RoleDO, Integer> {
    private IRoleMapper roleMapper;
    private IUserRoleMapper userRoleMapper;
    private IPermissionMapper permissionMapper;
    private IRolePermissionMapper rolePermissionMapper;

    public RoleService(IRoleMapper roleMapper, IUserRoleMapper userRoleMapper, IPermissionMapper permissionMapper,
            IRolePermissionMapper rolePermissionMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    public Integer add(RoleForm form) {
        checkNameNotInUse(roleMapper, form.getName(), null);
        if (!CacheLock.tryLock("add_role:" + form.getName())) {
            throw new BusinessException(MSG_NAME_IN_USE);
        }
        RoleDO entity = translate(form);
        roleMapper.add(entity);
        return entity.getId();
    }

    public void delete(Integer id) {
        checkExistById(roleMapper, id);
        if (userRoleMapper.countByRoleId(id) > 0) {
            throw new BusinessException("角色正在被使用，禁止删除");
        }
        roleMapper.deleteById(id);
        userRoleMapper.deleteByRoleId(id);
        rolePermissionMapper.deleteByRoleId(id);
    }

    public void update(Integer id, RoleForm form) {
        RoleDO old = checkExistById(roleMapper, id);
        if (old.getName().equals(form.getName())) {
            return;
        }
        checkNameNotInUse(roleMapper, form.getName(), null);
        RoleDO entity = translate(form);
        entity.setId(id);
        if (!CacheLock.tryLock("update_role:" + form.getName())) {
            throw new BusinessException(MSG_NAME_IN_USE);
        }
        roleMapper.updateById(entity);
    }

    public List<RoleDO> list() {
        return roleMapper.findAll();
    }

    public void updateRolePermissions(Integer id, UpdateRolePermissionsForm form) {
        checkExistById(roleMapper, id);
        Set<Integer> permissionIds = form.getPermissionIds();
        if (permissionMapper.countByIds(permissionIds) != permissionIds.size()) {
            throw new BusinessException("权限不存在");
        }
        rolePermissionMapper.deleteByRoleId(id);
        rolePermissionMapper.add(id, permissionIds);
    }

    public List<PermDO> findRolePermissions(Integer id) {
        return rolePermissionMapper.findPermissionsByRoleId(id);
    }

    private RoleDO translate(RoleForm form) {
        RoleDO entity = new RoleDO();
        entity.setName(form.getName());
        return entity;
    }

}
