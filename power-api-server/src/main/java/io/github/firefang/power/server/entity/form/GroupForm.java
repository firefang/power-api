package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("分组表单")
public class GroupForm {
    @ApiModelProperty("组名")
    @NotBlank
    @Length(max = 30)
    private String name;
}
