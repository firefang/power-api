package io.github.firefang.power.server.service.base;

import java.io.Serializable;

import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.mapper.IBaseMapper;
import io.github.firefang.power.server.mapper.INamedEntityMapper;

/**
 * @author xinufo
 *
 */
public abstract class BaseService<T, PK extends Serializable> {
    protected static final String MSG_NAME_IN_USE = "名称已被使用";
    protected static final String MSG_NOT_EXIST = "对象不存在";

    /**
     * 根据ID检查实体是否存在
     * 
     * @param id
     * @return
     * @throws BusinessException
     */
    protected T checkExistById(IBaseMapper<T, PK> mapper, PK id) throws BusinessException {
        T result = mapper.findOneById(id);
        if (result == null) {
            throw new BusinessException(MSG_NOT_EXIST);
        }
        return result;
    }

    /**
     * 检查实体名称是否被使用，被使用则抛出异常
     * 
     * @param mapper
     * @param name
     * @throws BusinessException 实体名称被使用时抛出异常
     */
    protected void checkNameNotInUse(INamedEntityMapper<?, PK> mapper, String name, PK parentId)
            throws BusinessException {
        if (mapper.findByName(name, parentId) != null) {
            throw new BusinessException(MSG_NAME_IN_USE);
        }
    }

}
