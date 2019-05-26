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

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;
import io.github.firefang.power.server.entity.domain.GroupDO;
import io.github.firefang.power.server.entity.domain.UserDO;
import io.github.firefang.power.server.entity.form.GroupForm;
import io.github.firefang.power.server.entity.form.UpdateGroupMemberForm;
import io.github.firefang.power.web.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinufo
 *
 */
@Api(tags = "分组接口")
@PermissionGroup("分组权限组")
@RestController
@RequestMapping("/groups")
public class GroupController {

    @ApiOperation("创建分组")
    @Permission(value = "创建分组", horizontalCheck = false)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Integer> add(@Validated @RequestBody GroupForm form) {
        return CommonResponse.<Integer>success().data(null);
    }

    @ApiOperation("删除分组")
    @Permission(value = "删除分组", horizontalCheck = false)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) {
    }

    @ApiOperation("修改分组")
    @Permission(value = "修改分组", horizontalCheck = false)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateById(@PathVariable("id") Integer id, @Validated @RequestBody GroupForm form) {
    }

    @ApiOperation("分页查询分组")
    @Permission(value = "查询分组", horizontalCheck = false)
    @GetMapping
    public CommonResponse<Page<GroupDO>> list(GroupDO condition,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size, @SortDefault Sort sort) {
        return CommonResponse.<Page<GroupDO>>success().data(null);
    }

    @ApiOperation("修改分组成员")
    @Permission(value = "修改分组成员", horizontalCheck = false)
    @PutMapping("/{id}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMembers(@PathVariable("id") Integer id, @Validated @RequestBody UpdateGroupMemberForm form) {
    }

    @ApiOperation("查询分组所有成员")
    @Permission(value = "查询分组所有成员", horizontalCheck = false)
    @GetMapping("/{id}/members")
    public CommonResponse<List<UserDO>> findMembers(@PathVariable("id") Integer id) {
        return CommonResponse.<List<UserDO>>success().data(null);
    }

}
