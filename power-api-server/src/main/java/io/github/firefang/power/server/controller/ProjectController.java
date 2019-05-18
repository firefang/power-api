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

import io.github.firefang.power.server.entity.domain.ProjectDO;
import io.github.firefang.power.server.entity.form.ProjectForm;
import io.github.firefang.power.server.entity.vo.ProjectDetailVO;
import io.github.firefang.power.server.entity.vo.ProjectOutLineVO;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "项目接口")
@RestController
@RequestMapping("/groups/{gid}/projects")
public class ProjectController {

    @ApiOperation("创建项目")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("gid") Integer groupId, @Validated @RequestBody ProjectForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除项目")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改项目")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateById(@PathVariable("id") Integer id, @Validated @RequestBody ProjectForm form) {
    }

    @ApiOperation("查询项目详情")
    @GetMapping("/{id}")
    public CommonResponse<ProjectDetailVO> info(@PathVariable("id") Integer id) {
        return CommonResponse.<ProjectDetailVO>success().data(null);
    }

    @ApiOperation("分页查询项目")
    @GetMapping
    public CommonResponse<Page<ProjectOutLineVO>> outline(@PathVariable("gid") Integer groupId, ProjectDO condition,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size, @SortDefault Sort sort) {
        return CommonResponse.<Page<ProjectOutLineVO>>success().data(null);
    }

}
