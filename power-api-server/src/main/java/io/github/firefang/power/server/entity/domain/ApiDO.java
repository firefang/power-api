package io.github.firefang.power.server.entity.domain;

import java.util.List;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class ApiDO {
    private Integer id;
    private Integer projectId;
    private String name;
    private String target;
    private String method;
    private String type;
    private List<String> paramTypes;
}
