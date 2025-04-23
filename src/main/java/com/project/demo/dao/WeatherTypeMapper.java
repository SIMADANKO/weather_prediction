package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.WeatherType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 天气类型：(WeatherType)Mapper接口
 *
 */
@Mapper
public interface WeatherTypeMapper extends BaseMapper<WeatherType>{

}
