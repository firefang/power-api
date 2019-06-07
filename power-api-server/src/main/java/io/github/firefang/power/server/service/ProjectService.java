package io.github.firefang.power.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.firefang.power.page.IPageableService;
import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.server.entity.domain.ProjectDO;
import io.github.firefang.power.server.entity.form.ProjectForm;
import io.github.firefang.power.server.entity.vo.ProjectDetailVO;
import io.github.firefang.power.server.entity.vo.ProjectDetailVO.ProjectDetailVOBuilder;
import io.github.firefang.power.server.entity.vo.ProjectOutLineVO;
import io.github.firefang.power.server.mapper.IProjectMapper;
import io.github.firefang.power.server.service.base.BaseService;

/**
 * @author xinufo
 *
 */
@Service
public class ProjectService extends BaseService<ProjectDO, Integer>
        implements IPageableService<ProjectDO, ProjectOutLineVO> {
    private IProjectMapper projectMapper;

    public ProjectService(IProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public Integer add(Integer groupId, ProjectForm form) {
        ProjectDO entity = translate(form);
        entity.setGroupId(groupId);
        saveUniqueFieldSafely(() -> projectMapper.add(entity));
        return entity.getId();
    }

    public void deleteById(Integer id) {
        checkExistById(projectMapper, id);
        projectMapper.deleteById(id);
        // TODO delete children
    }

    public void updateById(Integer id, ProjectForm form) {
        checkExistById(projectMapper, id);
        ProjectDO entity = translate(form);
        entity.setId(id);
        saveUniqueFieldSafely(() -> projectMapper.updateById(entity));
    }

    public ProjectDetailVO findById(Integer id) {
        ProjectDO project = projectMapper.findOneById(id);
        if (project == null) {
            return null;
        }
        ProjectDetailVOBuilder builder = ProjectDetailVO.builder();
        builder.id(project.getId()).name(project.getName()).remark(project.getRemark())
                .encryptClass(project.getEncryptClass());
        // TODO find children
        return builder.build();
    }

    @Override
    public Long count(ProjectDO condition) {
        return projectMapper.count(condition);
    }

    @Override
    public List<ProjectOutLineVO> find(ProjectDO condition, Pagination pagination) {
        return projectMapper.find(condition, pagination).stream().map(p -> {
            return ProjectOutLineVO.builder().id(p.getId()).name(p.getName()).remark(p.getRemark()).build();
        }).collect(Collectors.toList());
    }

    private ProjectDO translate(ProjectForm form) {
        ProjectDO entity = new ProjectDO();
        entity.setName(form.getName());
        entity.setRemark(form.getRemark());
        entity.setEncryptClass(form.getEncryptClass());
        return entity;
    }

}
