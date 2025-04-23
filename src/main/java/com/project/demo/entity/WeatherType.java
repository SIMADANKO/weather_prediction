package com.project.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 天气类型：(WeatherType)表实体类
 *
 */
@TableName("`weather_type`")
@Data
@EqualsAndHashCode(callSuper = false)
public class WeatherType implements Serializable {

    // WeatherType编号
    @TableId(value = "weather_type_id", type = IdType.AUTO)
    private Integer weather_type_id;

    // 天气类型
    @TableField(value = "`weather_type`")
    private String weather_type;























	// 更新时间
    @TableField(value = "update_time")
    private Timestamp update_time;

    // 创建时间
    @TableField(value = "create_time")
    private Timestamp create_time;

}
