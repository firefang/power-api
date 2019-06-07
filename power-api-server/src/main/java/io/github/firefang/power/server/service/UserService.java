package io.github.firefang.power.server.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.exception.NoPermissionException;
import io.github.firefang.power.page.IPageableService;
import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.auth.TokenHelper;
import io.github.firefang.power.server.cache.TokenCache;
import io.github.firefang.power.server.entity.domain.GroupDO;
import io.github.firefang.power.server.entity.domain.RoleDO;
import io.github.firefang.power.server.entity.domain.UserAuthDO;
import io.github.firefang.power.server.entity.domain.UserDO;
import io.github.firefang.power.server.entity.domain.UserInfoDO;
import io.github.firefang.power.server.entity.form.CreateUserForm;
import io.github.firefang.power.server.entity.form.UpdatePasswordForm;
import io.github.firefang.power.server.entity.form.UpdateUserForm;
import io.github.firefang.power.server.entity.form.UpdateUserRolesForm;
import io.github.firefang.power.server.mapper.IRoleMapper;
import io.github.firefang.power.server.mapper.IUserAuthMapper;
import io.github.firefang.power.server.mapper.IUserGroupMapper;
import io.github.firefang.power.server.mapper.IUserInfoMapper;
import io.github.firefang.power.server.mapper.IUserMapper;
import io.github.firefang.power.server.mapper.IUserRoleMapper;
import io.github.firefang.power.server.service.base.BaseService;
import io.github.firefang.power.web.util.CurrentRequestUtil;

/**
 * @author xinufo
 *
 */
@Service
public class UserService extends BaseService<UserInfoDO, Integer> implements IPageableService<UserDO, UserDO> {
    private IUserAuthMapper authMapper;
    private IUserInfoMapper infoMapper;
    private IUserMapper userMapper;
    private IUserGroupMapper userGroupMapper;
    private IRoleMapper roleMapper;
    private IUserRoleMapper userRoleMapper;
    private PasswordEncoder encoder;

    public UserService(IUserAuthMapper authMapper, IUserInfoMapper infoMapper, IUserMapper userMapper,
            IUserGroupMapper userGroupMapper, IRoleMapper roleMapper, IUserRoleMapper userRoleMapper,
            PasswordEncoder encoder) {
        this.authMapper = authMapper;
        this.infoMapper = infoMapper;
        this.userMapper = userMapper;
        this.userGroupMapper = userGroupMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.encoder = encoder;
    }

    public Integer add(CreateUserForm form) {
        String username = form.getUsername();

        Set<Integer> roleIds = form.getRoleIds();
        checkRoleExist(roleIds);
        checkSa(null, roleIds);

        UserAuthDO auth = new UserAuthDO();
        auth.setUsername(username);
        String password = encoder.encode(form.getPassword());
        auth.setPassword(password);
        saveUniqueFieldSafely(() -> authMapper.add(auth));

        Integer id = auth.getId();
        UserInfoDO info = new UserInfoDO();
        info.setId(id);
        infoMapper.add(info);

        userRoleMapper.deleteByUserId(id);
        userRoleMapper.add(id, roleIds);

        return id;
    }

    public void deleteById(Integer id) {
        checkExistById(infoMapper, id);
        Set<Integer> roleIds = userRoleMapper.findIdsByUserId(id);
        if (roleMapper.isSuperAdmin(roleIds)) {
            throw new BusinessException("不允许删除超级管理员");
        }
        authMapper.deleteById(id);
        infoMapper.deleteById(id);
        userRoleMapper.deleteByUserId(id);
        userGroupMapper.deleteByUserId(id);
    }

    public void updateById(Integer id, UpdateUserForm form) {
        checkExistById(infoMapper, id);
        UserInfoDO info = new UserInfoDO();
        info.setId(id);
        info.setEmail(form.getEmail());
        info.setNickname(form.getNickname());
        saveUniqueFieldSafely(() -> infoMapper.updateById(info));
    }

    public void updatePassword(Integer id, UpdatePasswordForm form) {
        UserAuthDO auth = authMapper.findOneById(id);
        if (auth == null) {
            throw new BusinessException(MSG_NOT_EXIST);
        }
        if (!encoder.matches(form.getOldPwd(), auth.getPassword())) {
            throw new BusinessException("密码不匹配");
        }
        String password = encoder.encode(form.getNewPwd());
        auth.setPassword(password);
        auth.setTokenKey(null); // 使该用户所有Token失效
        authMapper.updateById(auth);
        String token = CurrentRequestUtil.getHeader(IPowerConstants.TOKEN_KEY);
        TokenCache.invalid(token);
    }

    public UserDO info(Integer id) {
        return userMapper.findOneById(id);
    }

    @Override
    public Long count(UserDO condition) {
        return userMapper.count(condition);
    }

    @Override
    public List<UserDO> find(UserDO condition, Pagination pagination) {
        return userMapper.find(condition, pagination);
    }

    public List<GroupDO> findGroups(Integer id) {
        return userGroupMapper.findGroupsByUserId(id);
    }

    public void updateUserRoles(Integer uid, UpdateUserRolesForm form) {
        Set<Integer> roleIds = form.getRoleIds();
        checkRoleExist(roleIds);
        checkSa(uid, roleIds);
        userRoleMapper.deleteByUserId(uid);
        userRoleMapper.add(uid, roleIds);
    }

    public List<RoleDO> findUserRoles(Integer uid) {
        return userRoleMapper.findByUserId(uid);
    }

    private void checkRoleExist(Set<Integer> roleIds) {
        if (roleIds.size() != roleMapper.countByIds(roleIds)) {
            throw new BusinessException("角色不存在");
        }
    }

    private void checkSa(Integer targetUserId, Set<Integer> roldIds) {
        Integer uid = TokenHelper.decodeUserIdFromCurrentRequest();
        Set<Integer> operatorRoleIds = userRoleMapper.findIdsByUserId(uid);
        Boolean isOperatorSa = roleMapper.isSuperAdmin(operatorRoleIds);
        if (isOperatorSa != null && isOperatorSa) {
            // 操作者是超级管理员
            return;
        }
        Boolean isSetSa = roleMapper.isSuperAdmin(roldIds); // 要设置的角色是否为SA
        if (isSetSa != null && isSetSa) {
            // 非超级管理员设置超级管理员角色
            throw new NoPermissionException();
        } else if (targetUserId != null) {
            // 非超级管理员取消超级管理员角色
            Set<Integer> oldRoleIds = userRoleMapper.findIdsByUserId(targetUserId);
            Boolean isSaBefore = roleMapper.isSuperAdmin(oldRoleIds);
            if (isSaBefore != null && isSaBefore) {
                throw new NoPermissionException();
            }
        }
    }

}
