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
@ApiModel("Header表单")
public class HeaderForm {
    @ApiModelProperty("Header key")
    @NotBlank
    @Length(max = 30)
    private String key;

    @ApiModelProperty("Header值")
    @NotBlank
    @Length(max = 255)
    private String value;

    @ApiModelProperty("父节点类型")
    @NotNull
    private NodeType parentType;
}
