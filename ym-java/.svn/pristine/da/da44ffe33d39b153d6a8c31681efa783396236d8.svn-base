package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wangZhenWei
 */
@Data
public class RemovalCountVO {

    @ApiModelProperty(value = "已界定户数汇总")
    private Integer satisfyHourse;

    @ApiModelProperty(value = "未界定户数汇总")
    private Integer notSatisfyHourse;

    @ApiModelProperty(value = "序号")
    private String serialNumber;

    @ApiModelProperty(value = "总户数汇总")
    private Integer sumHourse;

    @ApiModelProperty(value = "未界定人数汇总")
    private Integer notSatisfyNumber;

    @ApiModelProperty(value = "已界定人数汇总")
    private Integer satisfyNumber;

    @ApiModelProperty(value = "总人数汇总")
    private Integer sumNumber;

    @ApiModelProperty(value = "行政区域父级编码")
    private String parentcode;

    @ApiModelProperty(value = "行政区域编码")
    private String citycode;

    @ApiModelProperty(value = "行政区域级别")
    private String level;

    @ApiModelProperty(value = "行政区域名称")
    private String name;

    private Integer state;

    @ApiModelProperty(value = "行政区域子集")
    private List<RemovalCountVO> children;

    @ApiModelProperty(value = "行政区域父级编码")
    private List<String> parentcodes;

    private String mergerShortName;

}