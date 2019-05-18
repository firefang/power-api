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
public class ProjectDetailVO {
    private Integer id;
    private String name;
    private String remark;
    private String encryptClass;
    private List<String> setups;
    private List<String> teardowns;
    private Map<String, String> vars;
    private Map<String, String> headers;
}
