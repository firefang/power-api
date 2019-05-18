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

import io.github.firefang.power.server.entity.domain.HeaderDO;
import io.github.firefang.power.server.entity.form.HeaderForm;
import io.github.firefang.power.server.entity.form.NodeType;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "Header接口")
@RestController
@RequestMapping("/parent/{pid}/headers")
public class HeaderController {

    @ApiOperation("创建Header")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer parentId, @Validated @RequestBody HeaderForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除Header")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改Header")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody HeaderForm form) {
    }

    @ApiOperation("查询节点下所有Header")
    @GetMapping
    public CommonResponse<List<HeaderDO>> info(@PathVariable("pid") Integer parentId,
            @RequestParam("parentType") NodeType parentType) {
        return CommonResponse.<List<HeaderDO>>success().data(null);
    }

}
