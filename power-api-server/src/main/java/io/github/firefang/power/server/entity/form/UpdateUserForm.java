package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@ApiModel("修改用户信息表单")
@Data
public class UpdateUserForm {
    @ApiModelProperty("昵称")
    @Length(max = 8)
    private String nickname;

    @ApiModelProperty("电子邮箱")
    @Email
    private String email;
}
