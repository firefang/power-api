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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.server.entity.domain.SetupTeardownDO;
import io.github.firefang.power.server.entity.form.NodeType;
import io.github.firefang.power.server.entity.form.SetupTeardownForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "SetupTeardown接口")
@RestController
@RequestMapping("/parent/{pid}/setupteardowns")
public class SetupTeardownController {
    @ApiOperation("创建SetupTeardown")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer parentId,
            @Validated @RequestBody SetupTeardownForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除SetupTeardown")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改SetupTeardown")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody SetupTeardownForm form) {
    }

    @ApiOperation("查询节点下所有SetupTeardown")
    @GetMapping
    public CommonResponse<List<SetupTeardownDO>> info(@PathVariable("pid") Integer parentId,
            @RequestParam("parentType") NodeType parentType) {
        return CommonResponse.<List<SetupTeardownDO>>success().data(null);
    }

}
