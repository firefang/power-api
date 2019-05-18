package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("角色表单")
public class RoleForm {
    @ApiModelProperty("角色名称")
    @NotBlank
    private String name;
}
