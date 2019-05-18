package io.github.firefang.power.server.entity.domain;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class UserDO {
    private Integer id;
    private String username;
    private String nickname;
    private String email;
}
