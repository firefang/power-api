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

import io.github.firefang.power.server.entity.form.ApiForm;
import io.github.firefang.power.server.entity.vo.ApiDetailVO;
import io.github.firefang.power.server.entity.vo.ApiOutLineVO;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "API接口")
@RestController
@RequestMapping("/groups/{gid}/projects/{pid}/apis")
public class ApiController {

    @ApiOperation("创建接口")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@PathVariable("pid") Integer projectId, @Validated @RequestBody ApiForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除接口")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改接口")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable("id") Integer id, @Validated @RequestBody ApiForm form) {
    }

    @ApiOperation("查询接口详情")
    @GetMapping("/{id}")
    public CommonResponse<ApiDetailVO> info(@PathVariable("id") Integer id) {
        return CommonResponse.<ApiDetailVO>success().data(null);
    }

    @ApiOperation("查询项目下所有接口")
    @GetMapping
    public CommonResponse<List<ApiOutLineVO>> findByProjectId(@PathVariable("pid") Integer projectId) {
        return CommonResponse.<List<ApiOutLineVO>>success().data(null);
    }

}
