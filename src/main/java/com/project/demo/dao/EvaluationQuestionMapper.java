package com.project.demo.dao;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.EvaluationQuestion;
import org.apache.ibatis.annotations.Mapper;


/**
 * 测评问卷题目：Mapper接口
 *
 */
@Mapper
public interface EvaluationQuestionMapper extends BaseMapper<EvaluationQuestion>
{
}
