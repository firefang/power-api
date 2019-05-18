package io.github.firefang.power.server.entity.form;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@ApiModel("接口表单")
@Data
public class ApiForm {

    @ApiModelProperty("接口名称")
    @NotBlank
    @Length(max = 30)
    private String name;

    @ApiModelProperty("接口请求地址")
    @NotBlank
    @Length(max = 255)
    private String target;

    @ApiModelProperty("请求方法")
    @NotBlank
    @Length(max = 255)
    private String method;

    @ApiModelProperty("接口类型")
    @NotBlank
    @Length(max = 15)
    private String type;

    @ApiModelProperty("参数类型列表")
    private List<String> paramTypes;
}
