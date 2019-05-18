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
@ApiModel("加密参数表单")
public class EncryptParamForm {
    @ApiModelProperty("参数名称")
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty("参数值")
    @NotBlank
    @Length(max = 255)
    private String value;
}
