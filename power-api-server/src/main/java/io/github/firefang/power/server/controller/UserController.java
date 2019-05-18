package io.github.firefang.power.server.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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

import io.github.firefang.power.server.entity.domain.GroupDO;
import io.github.firefang.power.server.entity.domain.RoleDO;
import io.github.firefang.power.server.entity.domain.UserDO;
import io.github.firefang.power.server.entity.form.CreateUserForm;
import io.github.firefang.power.server.entity.form.UpdatePasswordForm;
import io.github.firefang.power.server.entity.form.UpdateUserForm;
import io.github.firefang.power.server.entity.form.UpdateUserRolesForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/users")
public class UserController {

    @ApiOperation("创建用户")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@Validated @RequestBody CreateUserForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改自身信息")
    @PutMapping("/self")
    public void updateSelf(@Validated @RequestBody UpdateUserForm form) {
    }

    @ApiOperation("修改自身密码")
    @PutMapping("/self/password")
    public void updateSelfPassword(@Validated @RequestBody UpdatePasswordForm form) {
    }

    @ApiOperation("查询用户详情")
    @GetMapping("/{id}")
    public CommonResponse<UserDO> info(@PathVariable("id") Integer id) {
        return CommonResponse.<UserDO>success().data(null);
    }

    @ApiOperation("分页查询用户")
    @GetMapping
    public CommonResponse<Page<UserDO>> list(UserDO condition,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size, @SortDefault Sort sort) {
        return CommonResponse.<Page<UserDO>>success().data(null);
    }

    @ApiOperation("查询自身分组")
    @GetMapping("/self/groups")
    public CommonResponse<List<GroupDO>> findGroups() {
        return CommonResponse.<List<GroupDO>>success().data(null);
    }

    @ApiOperation("修改用户角色")
    @PutMapping("/{id}/roles")
    public void updateUserRoles(@Validated @RequestBody UpdateUserRolesForm form) {
    }

    @ApiOperation("查询用户角色")
    @GetMapping("/{id}/roles")
    public CommonResponse<List<RoleDO>> findUserRoles(@PathVariable("id") Integer id) {
        return CommonResponse.<List<RoleDO>>success().data(null);
    }

}
