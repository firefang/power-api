package io.github.firefang.power.server.controller;

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

import io.github.firefang.power.server.entity.domain.RequestParamDO;
import io.github.firefang.power.server.entity.form.RequestParamForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "请求参数接口")
@RestController
@RequestMapping("/parent/{pid}/requestparams")
public class RequestParamController {

    @ApiOperation("创建请求参数")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer parentId,
            @Validated @RequestBody RequestParamForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除请求参数")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改请求参数")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody RequestParamForm form) {
    }

    @ApiOperation("查询案例下所有请求参数")
    @GetMapping
    public CommonResponse<RequestParamDO> info(@PathVariable("pid") Integer parentId) {
        return CommonResponse.<RequestParamDO>success().data(null);
    }

}
