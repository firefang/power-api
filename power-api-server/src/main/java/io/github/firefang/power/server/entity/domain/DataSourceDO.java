package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class DataSourceDO {
    private Integer id;
    private Integer groupId;
    private String name;
    private String dbType;
    private String jdbcStr;
    private String username;
    private String password;
}
