package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.OrdinaryUsers;
import org.apache.ibatis.annotations.Mapper;

/**
 * 普通用户：(OrdinaryUsers)Mapper接口
 *
 */
@Mapper
public interface OrdinaryUsersMapper extends BaseMapper<OrdinaryUsers>{

}
