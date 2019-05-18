package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class UserAuthDO {
    private Integer id;
    private String username;
    private String password;
    private String tokenKey;
}
