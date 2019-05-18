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
@ApiModel("修改角色权限表单")
public class UpdateRolePermissionsForm {
    @ApiModelProperty("权限ID列表")
    @NotEmpty
    private Set<Integer> permissionIds;
}
