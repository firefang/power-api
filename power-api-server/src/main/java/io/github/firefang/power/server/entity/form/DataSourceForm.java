package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("数据源表单")
public class DataSourceForm {
    @NotBlank
    @ApiModelProperty("数据源名称")
    private String name;

    @NotBlank
    @ApiModelProperty("数据库类型")
    private String dbType;

    @NotBlank
    @ApiModelProperty("JDBC连接字符串")
    private String jdbcStr;

    @NotBlank
    @ApiModelProperty("数据库用户名")
    private String username;

    @NotBlank
    @ApiModelProperty("数据库密码")
    private String password;
}
