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
@ApiModel("案例表单")
@Data
public class CaseForm {
    @ApiModelProperty("案例名称")
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty("引用案例时的Key")
    @Length(min = 1, max = 50)
    private String key;
}
