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
 * 天气信息：(WeatherInformation)表实体类
 *
 */
@TableName("`weather_information`")
@Data
@EqualsAndHashCode(callSuper = false)
public class WeatherInformation implements Serializable {

    // WeatherInformation编号
    @TableId(value = "weather_information_id", type = IdType.AUTO)
    private Integer weather_information_id;

    // 地区名称
    @TableField(value = "`region_name`")
    private String region_name;
    // 发布时间
    @TableField(value = "`release_time`")
    private Timestamp release_time;
    // 天气图片
    @TableField(value = "`weather_pictures`")
    private String weather_pictures;
    // 天气类型
    @TableField(value = "`weather_type`")
    private String weather_type;
    // 降雨量
    @TableField(value = "`rainfall`")
    private Double rainfall;
    // 天气详情
    @TableField(value = "`weather_details`")
    private String weather_details;

    // 点击数
    @TableField(value = "hits")
    private Integer hits;

    // 点赞数
    @TableField(value = "praise_len")
    private Integer praise_len;

    // 收藏数
    @TableField(value = "collect_len")
    private Integer collect_len;

    // 评论数
    @TableField(value = "comment_len")
    private Integer comment_len;













	// 详细地址
	@TableField(value = "mark_address")
	private String mark_address;
	
	// 详细地址经度
	@TableField(value = "mark_lng")
	private String mark_lng;
	
	// 详细地址纬度
	@TableField(value = "mark_lat")
	private String mark_lat;






	// 更新时间
    @TableField(value = "update_time")
    private Timestamp update_time;

    // 创建时间
    @TableField(value = "create_time")
    private Timestamp create_time;

}
