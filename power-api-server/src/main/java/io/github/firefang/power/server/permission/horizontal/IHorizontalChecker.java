package io.github.firefang.power.server.permission.horizontal;

import java.util.Map;

/**
 * 水平权限校验
 * 
 * @author xinufo
 *
 */
public interface IHorizontalChecker {

    /**
     * 返回处理的权限类型
     * 
     * @return
     */
    String type();

    /**
     * 校验是否有权操作
     * 
     * @param params
     * @param extra
     * @return 有权操作返回true，否则返回false
     */
    boolean check(Map<String, Object> params, Map<String, Object> extra);

}
