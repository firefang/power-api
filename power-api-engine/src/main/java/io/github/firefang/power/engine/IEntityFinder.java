package io.github.firefang.power.engine;

import java.util.List;

import io.github.firefang.power.engine.entity.PowerApiDO;
import io.github.firefang.power.engine.entity.PowerCaseDO;
import io.github.firefang.power.engine.entity.PowerProjectDO;

/**
 * Finder used by DefaultCaseRunner to find entities
 * 
 * @author xinufo
 *
 */
public interface IEntityFinder {

    /**
     * Find project entity by project id
     * 
     * @param id
     * @return
     */
    PowerProjectDO getProjectById(Integer id);

    /**
     * Find api entity by api id
     * 
     * @param id
     * @return
     */
    PowerApiDO getApiById(Integer id);

    /**
     * Find all api entities in the project
     * 
     * @param projectId
     * @return
     */
    List<PowerApiDO> getApisByProjectId(Integer projectId);

    /**
     * Find case entity by id
     * 
     * @param id
     * @return
     */
    PowerCaseDO getCaseById(Integer id);

    /**
     * Find case entity by key
     * 
     * @param key
     * @return
     */
    PowerCaseDO getCaseByKey(String key);

    /**
     * Find all case entities in the api
     * 
     * @param apiId
     * @return
     */
    List<PowerCaseDO> getCasesByApiId(Integer apiId);

}
