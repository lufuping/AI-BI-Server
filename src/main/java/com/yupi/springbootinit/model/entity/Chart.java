package com.yupi.springbootinit.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * 图表
 */
@Data
public class Chart implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 生成图表的用户ID
     */
    private Long userId;

    /**
     * 分析目标
     */
    private String goel;

    /**
     * 名称
     */
    private String name;

    /**
     * 要分析的原始数据
     */
    private String chartData;

    /**
     * 生成图表的类型
     */
    private String chartType;

    /**
     * AI生成的图表
     */
    private String genChart;

    /**
     * AI生成的分析结果
     */
    private String genResult;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 执行信息
     */
    private String execMessage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Byte isDelete;

    private static final long serialVersionUID = 1L;
}