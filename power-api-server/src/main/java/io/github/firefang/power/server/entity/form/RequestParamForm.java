package io.github.firefang.power.server.entity.form;

import java.util.Map;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("请求参数表单")
public class RequestParamForm {
    @ApiModelProperty("参数列表")
    @NotNull
    private Map<String, Object> param;
}
