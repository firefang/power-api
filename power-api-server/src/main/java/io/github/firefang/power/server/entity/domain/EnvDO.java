package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class EnvDO {
    private Integer id;
    private Integer projectId;
    private String name;
    private String basePath;
    private boolean defaultEnv;
}
