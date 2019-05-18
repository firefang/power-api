package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@ApiModel("任务表单")
public class TaskForm {
    @ApiModelProperty("要执行的节点类型")
    @NotNull
    private NodeType type;

    @ApiModelProperty("要执行的节点ID")
    @NotNull
    private Integer id;
}
