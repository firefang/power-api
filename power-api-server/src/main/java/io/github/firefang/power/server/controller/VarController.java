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

import io.github.firefang.power.server.entity.domain.VarDO;
import io.github.firefang.power.server.entity.form.NodeType;
import io.github.firefang.power.server.entity.form.VarForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "变量接口")
@RestController
@RequestMapping("/parent/{pid}/vars")
public class VarController {

    @ApiOperation("创建变量")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer parentId, @Validated @RequestBody VarForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除变量")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改变量")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody VarForm form) {
    }

    @ApiOperation("查询节点下所有变量")
    @GetMapping
    public CommonResponse<List<VarDO>> info(@PathVariable("pid") Integer parentId,
            @RequestParam("parentType") NodeType parentType) {
        return CommonResponse.<List<VarDO>>success().data(null);
    }

}
