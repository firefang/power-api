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

import io.github.firefang.power.server.entity.domain.EnvDO;
import io.github.firefang.power.server.entity.form.EnvForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "环境接口")
@RestController
@RequestMapping("/projects/{pid}/envs")
public class EnvController {

    @ApiOperation("创建环境")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer projectId, @Validated @RequestBody EnvForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除环境")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改环境")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody EnvForm form) {
    }

    @ApiOperation("设置默认环境")
    @PutMapping("/{id}/default")
    @ResponseStatus(HttpStatus.CREATED)
    public void setDefault(@PathVariable("id") Integer id) {
    }

    @ApiOperation("查询项目下所有环境")
    @GetMapping
    public CommonResponse<List<EnvDO>> findByProjectId(@PathVariable("pid") Integer projectId) {
        return CommonResponse.<List<EnvDO>>success().data(null);
    }

}
