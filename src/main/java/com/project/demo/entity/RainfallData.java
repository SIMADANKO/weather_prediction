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
 * 降雨量数据：(RainfallData)表实体类
 *
 */
@TableName("`rainfall_data`")
@Data
@EqualsAndHashCode(callSuper = false)
public class RainfallData implements Serializable {

    // RainfallData编号
    @TableId(value = "rainfall_data_id", type = IdType.AUTO)
    private Integer rainfall_data_id;

    // 地区名称
    @TableField(value = "`region_name`")
    private String region_name;
    // 雨量平均值
    @TableField(value = "`average_rainfall`")
    private Double average_rainfall;
    // 雨量最大值
    @TableField(value = "`maximum_rainfall`")
    private Double maximum_rainfall;
    // 雨量最小值
    @TableField(value = "`minimum_rainfall`")
    private Double minimum_rainfall;
    // 雨量标准值
    @TableField(value = "`rainfall_standard_value`")
    private Double rainfall_standard_value;
    // 雨量预警值
    @TableField(value = "`rainfall_warning_value`")
    private Double rainfall_warning_value;
    // 数据详情
    @TableField(value = "`data_details`")
    private String data_details;























	// 更新时间
    @TableField(value = "update_time")
    private Timestamp update_time;

    // 创建时间
    @TableField(value = "create_time")
    private Timestamp create_time;

}
