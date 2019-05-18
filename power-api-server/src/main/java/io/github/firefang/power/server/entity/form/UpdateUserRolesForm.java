package io.github.firefang.power.server.entity.form;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("修改用户角色表单")
public class UpdateUserRolesForm {
    @ApiModelProperty("角色ID列表")
    @NotEmpty
    private Set<Integer> roleIds;
}
