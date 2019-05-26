package io.github.firefang.power.server.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

/**
 * @author xinufo
 *
 */
public interface INamedEntityMapper<T, PK extends Serializable> {

    /**
     * 根据名称查询，若不指定parentId则表达全局查找
     * 
     * @param name
     * @param parentId
     * @return
     */
    T findByName(@Param("name") String name, @Param("parentId") PK parentId);

}
