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

import io.github.firefang.power.server.entity.domain.DataSourceDO;
import io.github.firefang.power.server.entity.form.DataSourceForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "数据源接口")
@RestController
@RequestMapping("/groups/{gid}/datasources")
public class DataSourceController {

    @ApiOperation("创建数据源")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("gid") Integer groupId,
            @Validated @RequestBody DataSourceForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除数据源")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改数据源")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody DataSourceForm form) {
    }

    @ApiOperation("查询组内所有数据源")
    @GetMapping
    public CommonResponse<List<DataSourceDO>> info(@PathVariable("gid") Integer groupId) {
        return CommonResponse.<List<DataSourceDO>>success().data(null);
    }

}
