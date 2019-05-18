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
public class CaseDetailVO {
    private Integer id;
    private Integer apiId;
    private String name;
    private String key;
    private List<String> setups;
    private List<String> teardowns;
    private Map<String, String> vars;
    private Map<String, String> headers;
    private Map<String, Object> requestParams;
    private List<String> assertions;
}
