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
@ApiModel("项目表单")
public class ProjectForm {
    @ApiModelProperty("项目名称")
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty("项目描述")
    @Length(max = 255)
    private String remark;

    @ApiModelProperty("加密类全名")
    @Length(max = 255)
    private String encryptClass;
}
