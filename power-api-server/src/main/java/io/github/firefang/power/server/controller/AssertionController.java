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

import io.github.firefang.power.server.entity.domain.AssertionDO;
import io.github.firefang.power.server.entity.form.AssertionForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "断言接口")
@RestController
@RequestMapping("/parent/{pid}/assertions")
public class AssertionController {

    @ApiOperation("创建断言")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer parentId,
            @Validated @RequestBody AssertionForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除断言")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改断言")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody AssertionForm form) {
    }

    @ApiOperation("查询所有断言")
    @GetMapping
    public CommonResponse<List<AssertionDO>> info(@PathVariable("pid") Integer parentId) {
        return CommonResponse.<List<AssertionDO>>success().data(null);
    }

}
