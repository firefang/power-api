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
@ApiModel("SetupTeardown表单")
public class SetupTeardownForm {
    @ApiModelProperty("表达式")
    @NotBlank
    @Length(max = 255)
    private String expression;

    @ApiModelProperty("是否为Teardown")
    @NotNull
    private Boolean teardown;

    @ApiModelProperty("父节点类型")
    @NotNull
    private NodeType parentType;

}
