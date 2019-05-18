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
@ApiModel("环境表单")
public class EnvForm {
    @ApiModelProperty("环境名称")
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty("路径")
    @NotBlank
    @Length(max = 255)
    private String basePath;
}
