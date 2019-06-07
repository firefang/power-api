package io.github.firefang.power.server.service.base;

import java.io.Serializable;
import java.util.function.Supplier;

import org.springframework.dao.DuplicateKeyException;

import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.mapper.IBaseMapper;

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

    protected void saveUniqueFieldSafely(Supplier<?> mapper) {
        try {
            mapper.get();
        } catch (DuplicateKeyException e) {
            throw new BusinessException(MSG_NAME_IN_USE);
        }
    }

}
