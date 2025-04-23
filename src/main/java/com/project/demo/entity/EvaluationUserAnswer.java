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
 * 用户答题
 */
@TableName("evaluation_user_answer")
@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluationUserAnswer implements Serializable {

    private static final long serialVersionUID = -89845695959527153L;

    /**
     * 用户答题id：[0,10000]
     */
    @TableId(value = "user_answer_id", type = IdType.AUTO)
    private Integer user_answer_id;

    /**
     * 用户id
     */

    @TableField(value = "user_id")
    private Integer user_id;

    /**
     * 测评问卷id
     */

    @TableField(value = "exam_id")
    private Integer exam_id;

    /**
     * 分数
     */

    @TableField(value = "score")
    private Double score;

    /**
     * 答案
     */

    @TableField(value = "answers")
    private String answers;
	
	
	/**
	 * 点评状态
	 */
	
	@TableField(value = "comment_state")
	private Integer comment_state;

    /**
     * 提交人
     */

    @TableField(value = "nickname")
    private String nickname;

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
	
	/**
     * 评语
     */
    @TableField(value = "comment_desc")
    private String comment_desc;

}

