package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@ApiModel("修改用户密码表单")
@Data
public class UpdatePasswordForm {
    @ApiModelProperty("旧密码")
    @NotNull
    @Length(min = 6, max = 16)
    private String oldPwd;

    @ApiModelProperty("新密码")
    @NotNull
    @Length(min = 6, max = 16)
    private String newPwd;
}
