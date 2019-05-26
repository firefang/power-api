package io.github.firefang.power.server.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;
import io.github.firefang.power.server.auth.TokenHelper;
import io.github.firefang.power.server.entity.domain.GroupDO;
import io.github.firefang.power.server.entity.domain.RoleDO;
import io.github.firefang.power.server.entity.domain.UserDO;
import io.github.firefang.power.server.entity.form.CreateUserForm;
import io.github.firefang.power.server.entity.form.UpdatePasswordForm;
import io.github.firefang.power.server.entity.form.UpdateUserForm;
import io.github.firefang.power.server.entity.form.UpdateUserRolesForm;
import io.github.firefang.power.server.service.UserService;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "用户接口")
@PermissionGroup("用户权限组")
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userSrv;

    public UserController(UserService userSrv) {
        this.userSrv = userSrv;
    }

    @ApiOperation("创建用户")
    @Permission(value = "创建用户", horizontalCheck = false)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@Validated @RequestBody CreateUserForm form) {
        Integer id = userSrv.add(form);
        return CommonResponse.<Integer>success().data(id);
    }

    @ApiOperation("删除用户")
    @Permission(value = "删除用户", horizontalCheck = false)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) {
        userSrv.deleteById(id);
    }

    @ApiOperation("修改自身信息")
    @PutMapping("/self")
    public void updateSelf(@Validated @RequestBody UpdateUserForm form) {
        Integer uid = TokenHelper.decodeUserIdFromCurrentRequest();
        userSrv.updateById(uid, form);
    }

    @ApiOperation("修改自身密码")
    @PutMapping("/self/password")
    public void updateSelfPassword(@Validated @RequestBody UpdatePasswordForm form) {
        Integer uid = TokenHelper.decodeUserIdFromCurrentRequest();
        userSrv.updatePassword(uid, form);
    }

    @ApiOperation("查询用户详情")
    @Permission(value = "查询用户详情", horizontalCheck = false)
    @GetMapping("/{id}")
    public CommonResponse<UserDO> info(@PathVariable("id") Integer id) {
        UserDO result = userSrv.info(id);
        return CommonResponse.<UserDO>success().data(result);
    }

    @ApiOperation("分页查询用户")
    @Permission(value = "分页查询用户", horizontalCheck = false)
    @GetMapping
    public CommonResponse<Page<UserDO>> list(UserDO condition,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size) {
        Page<UserDO> result = userSrv.findByPage(condition, page, size, null);
        return CommonResponse.<Page<UserDO>>success().data(result);
    }

    @ApiOperation("查询自身分组")
    @GetMapping("/self/groups")
    public CommonResponse<List<GroupDO>> findGroups() {
        Integer uid = TokenHelper.decodeUserIdFromCurrentRequest();
        List<GroupDO> result = userSrv.findGroups(uid);
        return CommonResponse.<List<GroupDO>>success().data(result);
    }

    @ApiOperation("修改用户角色")
    @Permission(value = "修改用户角色", horizontalCheck = false)
    @PutMapping("/{id}/roles")
    public void updateUserRoles(@PathVariable("id") Integer id, @Validated @RequestBody UpdateUserRolesForm form) {
        userSrv.updateUserRoles(id, form);
    }

    @ApiOperation("查询用户角色")
    @Permission(value = "查询用户角色", horizontalCheck = false)
    @GetMapping("/{id}/roles")
    public CommonResponse<List<RoleDO>> findUserRoles(@PathVariable("id") Integer id) {
        List<RoleDO> result = userSrv.findUserRoles(id);
        return CommonResponse.<List<RoleDO>>success().data(result);
    }

}
