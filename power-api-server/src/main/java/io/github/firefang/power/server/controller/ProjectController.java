package io.github.firefang.power.server.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;
import io.github.firefang.power.permission.annotations.PermissionParam;
import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.entity.domain.ProjectDO;
import io.github.firefang.power.server.entity.form.ProjectForm;
import io.github.firefang.power.server.entity.vo.ProjectDetailVO;
import io.github.firefang.power.server.entity.vo.ProjectOutLineVO;
import io.github.firefang.power.server.service.ProjectService;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "项目接口")
@PermissionGroup("项目权限组")
@RestController
@RequestMapping("/groups/{gid}/projects")
public class ProjectController {
    public static final String PERM_PARAM_KEY = "groupId";
    private static final String PERM_EXTRA_PARAM = "{" + IPowerConstants.PERM_TYPE_KEY + ":'"
            + IPowerConstants.PERM_TYPE_PROJECT + "'}";

    private ProjectService projectSrv;

    public ProjectController(ProjectService projectSrv) {
        this.projectSrv = projectSrv;
    }

    @ApiOperation("创建项目")
    @Permission(value = "创建项目", extraParams = PERM_EXTRA_PARAM)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PermissionParam(name = PERM_PARAM_KEY) @PathVariable("gid") Integer groupId,
            @Validated @RequestBody ProjectForm form) {
        Integer id = projectSrv.add(groupId, form);
        return CommonResponse.<Integer>success().data(id);
    }

    @ApiOperation("删除项目")
    @Permission(value = "删除项目", extraParams = PERM_EXTRA_PARAM)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PermissionParam(name = PERM_PARAM_KEY) @PathVariable("gid") Integer groupId,
            @PathVariable("id") Integer id) {
        projectSrv.deleteById(id);
    }

    @ApiOperation("修改项目")
    @Permission(value = "修改项目", extraParams = PERM_EXTRA_PARAM)
    @PutMapping("/{id}")
    public void updateById(@PermissionParam(name = PERM_PARAM_KEY) @PathVariable("gid") Integer groupId,
            @PathVariable("id") Integer id, @Validated @RequestBody ProjectForm form) {
        projectSrv.updateById(id, form);
    }

    @ApiOperation("查询项目详情")
    @Permission(value = "查询项目详情", extraParams = PERM_EXTRA_PARAM)
    @GetMapping("/{id}")
    public CommonResponse<ProjectDetailVO> info(
            @PermissionParam(name = PERM_PARAM_KEY) @PathVariable("gid") Integer groupId,
            @PathVariable("id") Integer id) {
        ProjectDetailVO result = projectSrv.findById(id);
        return CommonResponse.<ProjectDetailVO>success().data(result);
    }

    @ApiOperation("分页查询项目")
    @Permission(value = "分页查询项目", extraParams = PERM_EXTRA_PARAM)
    @GetMapping
    public CommonResponse<Page<ProjectOutLineVO>> outline(
            @PermissionParam(name = PERM_PARAM_KEY) @PathVariable("gid") Integer groupId, ProjectDO condition,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size, @SortDefault Sort sort) {
        condition.setGroupId(groupId);
        Page<ProjectOutLineVO> result = projectSrv.findByPage(condition, page, size, sort);
        return CommonResponse.<Page<ProjectOutLineVO>>success().data(result);
    }

}
