package io.github.firefang.power.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;
import io.github.firefang.power.server.entity.domain.PermDO;
import io.github.firefang.power.server.entity.domain.RoleDO;
import io.github.firefang.power.server.entity.form.RoleForm;
import io.github.firefang.power.server.entity.form.UpdateRolePermissionsForm;
import io.github.firefang.power.server.service.RoleService;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "角色接口")
@PermissionGroup("角色权限组")
@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleSrv;

    public RoleController(RoleService roleSrv) {
        this.roleSrv = roleSrv;
    }

    @ApiOperation("创建角色")
    @Permission(value = "创建角色", horizontalCheck = false)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@Validated @RequestBody RoleForm form) {
        Integer id = roleSrv.add(form);
        return CommonResponse.<Integer>success().data(id);
    }

    @ApiOperation("删除角色")
    @Permission(value = "删除角色", horizontalCheck = false)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        roleSrv.delete(id);
    }

    @ApiOperation("修改角色")
    @Permission(value = "修改角色", horizontalCheck = false)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody RoleForm form) {
        roleSrv.update(id, form);
    }

    @ApiOperation("查询所有角色")
    @Permission(value = "查询所有角色", horizontalCheck = false)
    @GetMapping
    public CommonResponse<List<RoleDO>> list() {
        List<RoleDO> result = roleSrv.list();
        return CommonResponse.<List<RoleDO>>success().data(result);
    }

    @ApiOperation("修改角色拥有的权限")
    @Permission(value = "修改角色权限", horizontalCheck = false)
    @PutMapping("/{id}/permissions")
    public void updateRolePermissions(@PathVariable("id") Integer id,
            @Validated @RequestBody UpdateRolePermissionsForm form) {
        roleSrv.updateRolePermissions(id, form);
    }

    @ApiOperation("查询角色拥有的权限")
    @Permission(value = "查询角色权限", horizontalCheck = false)
    @GetMapping("/{id}/permissions")
    public CommonResponse<List<PermDO>> findRolePermissions(@PathVariable("id") Integer id) {
        List<PermDO> permissions = roleSrv.findRolePermissions(id);
        return CommonResponse.<List<PermDO>>success().data(permissions);
    }

}
