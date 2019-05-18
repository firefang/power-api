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

import io.github.firefang.power.server.entity.domain.PermDO;
import io.github.firefang.power.server.entity.domain.RoleDO;
import io.github.firefang.power.server.entity.form.RoleForm;
import io.github.firefang.power.server.entity.form.UpdateRolePermissionsForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @ApiOperation("创建角色")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer projectId, @Validated @RequestBody RoleForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改角色")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody RoleForm form) {
    }

    @ApiOperation("查询所有角色")
    @GetMapping
    public CommonResponse<List<RoleDO>> findByProjectId() {
        return CommonResponse.<List<RoleDO>>success().data(null);
    }

    @ApiOperation("修改角色拥有的权限")
    @PutMapping("/{id}/permissions")
    public void updateRolePermissions(@Validated @RequestBody UpdateRolePermissionsForm form) {
    }

    @ApiOperation("查询角色拥有的权限")
    @GetMapping("/{id}/permissions")
    public CommonResponse<List<PermDO>> findRolePermissions(@PathVariable("id") Integer id) {
        return CommonResponse.<List<PermDO>>success().data(null);
    }

}
