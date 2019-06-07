package io.github.firefang.power.server.permission.horizontal.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.controller.ProjectController;
import io.github.firefang.power.server.mapper.IUserGroupMapper;

/**
 * @author xinufo
 *
 */
@Component
public class ProjectHorizontalChecker extends BaseGroupResourceHorizontalChecker {

    public ProjectHorizontalChecker(IUserGroupMapper userGroupMapper) {
        super(userGroupMapper);
    }

    @Override
    public String type() {
        return IPowerConstants.PERM_TYPE_PROJECT;
    }

    @Override
    protected Integer getGroupId(Map<String, Object> params, Map<String, Object> extra) {
        return (Integer) params.get(ProjectController.PERM_PARAM_KEY);
    }

}
