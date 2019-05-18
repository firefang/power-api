package io.github.firefang.power.server.entity.form;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@ApiModel("创建用户表单")
@Data
public class CreateUserForm {
    @ApiModelProperty("用户名")
    @NotNull
    @Length(min = 4, max = 8)
    private String username;

    @ApiModelProperty("密码")
    @NotNull
    @Length(min = 6, max = 16)
    private String password;

    @ApiModelProperty("角色ID列表")
    @NotEmpty
    private Set<Integer> roleIds;
}
