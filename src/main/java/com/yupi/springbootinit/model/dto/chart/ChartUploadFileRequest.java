package com.yupi.springbootinit.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * @author 小鹿
 * @from /
 */
@Data
public class ChartUploadFileRequest implements Serializable {

    /**
     * 目标
     */
    private String goel;

    /**
     * 图表类型
     */
    private String chartType;

    /**
     * 名称
     */
    private String name;

    /**
     * 源数据
     */
    private String chartData;

    private static final long serialVersionUID = 1L;
}