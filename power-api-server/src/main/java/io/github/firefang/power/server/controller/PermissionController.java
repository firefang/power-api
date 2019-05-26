package io.github.firefang.power.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;
import io.github.firefang.power.server.entity.domain.PermDO;
import io.github.firefang.power.server.service.PermissionService;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "权限接口")
@PermissionGroup("权限相关权限组")
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private PermissionService permissionSrv;

    public PermissionController(PermissionService permissionSrv) {
        this.permissionSrv = permissionSrv;
    }

    @ApiOperation("查询所有权限")
    @Permission(value = "查询所有权限", horizontalCheck = false)
    @GetMapping
    public CommonResponse<Map<String, List<PermDO>>> list() {
        Map<String, List<PermDO>> result = permissionSrv.list();
        return CommonResponse.<Map<String, List<PermDO>>>success().data(result);
    }

}
