package io.github.firefang.power.server.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.page.IPageableService;
import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.server.cache.CacheLock;
import io.github.firefang.power.server.entity.domain.GroupDO;
import io.github.firefang.power.server.entity.domain.UserDO;
import io.github.firefang.power.server.entity.form.GroupForm;
import io.github.firefang.power.server.entity.form.UpdateGroupMemberForm;
import io.github.firefang.power.server.mapper.IGroupMapper;
import io.github.firefang.power.server.mapper.IUserAuthMapper;
import io.github.firefang.power.server.mapper.IUserGroupMapper;
import io.github.firefang.power.server.service.base.BaseService;

/**
 * @author xinufo
 *
 */
@Service
public class GroupService extends BaseService<GroupDO, Integer> implements IPageableService<GroupDO, GroupDO> {
    private IGroupMapper groupMapper;
    private IUserGroupMapper userGroupMapper;
    private IUserAuthMapper userAuthMapper;

    public GroupService(IGroupMapper groupMapper, IUserGroupMapper userGroupMapper, IUserAuthMapper userAuthMapper) {
        this.groupMapper = groupMapper;
        this.userGroupMapper = userGroupMapper;
        this.userAuthMapper = userAuthMapper;
    }

    public Integer add(GroupForm form) {
        checkNameNotInUse(groupMapper, form.getName(), null);
        GroupDO entity = translate(form);
        if (CacheLock.tryLock("add_group:" + form.getName())) {
            throw new BusinessException(MSG_NAME_IN_USE);
        }
        groupMapper.add(entity);
        return entity.getId();
    }

    public void deleteById(Integer id) {
        checkExistById(groupMapper, id);
        groupMapper.deleteById(id);
        userGroupMapper.deleteByGroupId(id);
    }

    public void updateById(Integer id, GroupForm form) {
        GroupDO old = checkExistById(groupMapper, id);
        if (old.getName().equals(form.getName())) {
            return;
        }
        checkNameNotInUse(groupMapper, form.getName(), null);
        GroupDO entity = translate(form);
        if (CacheLock.tryLock("update_group:" + form.getName())) {
            throw new BusinessException(MSG_NAME_IN_USE);
        }
        entity.setId(id);
        groupMapper.updateById(entity);
    }

    @Override
    public Long count(GroupDO condition) {
        return groupMapper.count(condition);
    }

    @Override
    public List<GroupDO> find(GroupDO condition, Pagination pagination) {
        return groupMapper.find(condition, pagination);
    }

    public void updateMembers(Integer id, UpdateGroupMemberForm form) {
        checkExistById(groupMapper, id);
        Set<Integer> uids = form.getUserIds();
        if (userAuthMapper.count(uids) != uids.size()) {
            throw new BusinessException("用户不存在");
        }
        userGroupMapper.deleteByGroupId(id);
        userGroupMapper.add(uids, id);
    }

    public List<UserDO> findMembers(Integer id) {
        return userGroupMapper.findUsersByGroupId(id);
    }

    /**
     * 实体类型转换
     * 
     * @param form
     * @return
     */
    private GroupDO translate(GroupForm form) {
        GroupDO entity = new GroupDO();
        entity.setName(form.getName());
        return entity;
    }

}
