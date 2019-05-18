package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("变量表单")
public class VarForm {
    @ApiModelProperty("变量名")
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty("表达式")
    @NotBlank
    @Length(max = 255)
    private String expression;

    @ApiModelProperty("父节点类型")
    @NotNull
    private NodeType parentType;

}
