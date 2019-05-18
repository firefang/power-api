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

import io.github.firefang.power.server.entity.form.CaseForm;
import io.github.firefang.power.server.entity.vo.CaseDetailVO;
import io.github.firefang.power.server.entity.vo.CaseOutLineVO;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "案例接口")
@RestController
@RequestMapping("/groups/{gid}/projects/{pid}/apis/{aid}/cases")
public class CaseController {

    @ApiOperation("创建案例")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("aid") Integer apiId, @Validated @RequestBody CaseForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除案例")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改案例")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody CaseForm form) {
    }

    @ApiOperation("查询案例详情")
    @GetMapping("/{id}")
    public CommonResponse<CaseDetailVO> info(@PathVariable("id") Integer id) {
        return CommonResponse.<CaseDetailVO>success().data(null);
    }

    @ApiOperation("查询接口下所有案例")
    @GetMapping
    public CommonResponse<List<CaseOutLineVO>> findByApiId(@PathVariable("aid") Integer apiId) {
        return CommonResponse.<List<CaseOutLineVO>>success().data(null);
    }

}
