package io.github.firefang.power.server.entity.vo;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
@Builder
public class ApiDetailVO {
    private Integer id;
    private Integer projectId;
    private String name;
    private String target;
    private String method;
    private String type;
    private List<String> setups;
    private List<String> teardowns;
    private Map<String, String> vars;
    private Map<String, String> headers;
    private List<String> paramTypes;
    private Map<String, String> encryptParams;
}
