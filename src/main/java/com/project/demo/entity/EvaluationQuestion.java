package com.project.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 测评问卷题库
 */
@TableName("evaluation_question")
@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluationQuestion implements Serializable {

    private static final long serialVersionUID = -89845695959527153L;

    /**
     * 题库id：[0,10000]
     */
    @TableId(value = "exam_question_id", type = IdType.AUTO)
    private Integer exam_question_id;

    /**
     * 类型
     */

    @TableField(value = "type")
    private String type;

    /**
     * 题目
     */

    @TableField(value = "title")
    private String title;

    /**
     * 选项
     */

    @TableField(value = "question_item")
    private String question_item;

    
    /**
     * 排序
     */

    @TableField(value = "question_order")
    private Integer question_order;

    /**
     * 所属测评问卷
     */

    @TableField(value = "exam_id")
    private Integer exam_id;


    /**
     * 创建时间：
     */


    @TableField(value = "create_time")
    private Timestamp createTime;

    /**
     * 更新时间：
     */


    @TableField(value = "update_time")
    private Timestamp updateTime;

}

