package io.github.firefang.power.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.server.entity.domain.PermDO;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @ApiOperation("查询所有权限")
    @GetMapping
    public CommonResponse<Map<String, List<PermDO>>> findByProjectId() {
        return CommonResponse.<Map<String, List<PermDO>>>success().data(null);
    }

}
