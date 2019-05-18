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

import io.github.firefang.power.server.entity.domain.EncryptParamDO;
import io.github.firefang.power.server.entity.form.EncryptParamForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "加密参数接口")
@RestController
@RequestMapping("/parent/{pid}/encryptparams")
public class EncryptParamController {

    @ApiOperation("创建加密参数")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer apiId,
            @Validated @RequestBody EncryptParamForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除加密参数")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改加密参数")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody EncryptParamForm form) {
    }

    @ApiOperation("查询接口下所有加密参数")
    @GetMapping
    public CommonResponse<List<EncryptParamDO>> info(@PathVariable("pid") Integer apiId) {
        return CommonResponse.<List<EncryptParamDO>>success().data(null);
    }

}
