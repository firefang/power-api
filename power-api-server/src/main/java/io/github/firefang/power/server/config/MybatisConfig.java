package io.github.firefang.power.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import io.github.firefang.power.server.mapper.IMapperPackage;

/**
 * @author xinufo
 *
 */
@Configuration
@MapperScan(basePackageClasses = IMapperPackage.class)
public class MybatisConfig {

}
