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
@ApiModel("修改组成员表单")
public class UpdateGroupMemberForm {
    @ApiModelProperty("组成员ID列表")
    @NotEmpty
    private Set<Integer> userIds;
}
