package com.lyht.business.abm.land.bean;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(description = "周报汇总")
public interface RequisitionPlanDetail {


    /**
     *主键
     */


     Integer getId();
    /**
     *内码
     */
    @ApiModelProperty(value = "内码")
     String getNm();
    /**
     *行政区域
     */
    @ApiModelProperty(value = "行政区域")
    @Column(name = "region")
     String getRegion();

    /**
     *所属地块
     */
    @Column(name = "land_nm")
    @ApiModelProperty(value = "所属地块")
     String getLandNm();
    /**
     *施工编号
     */

    @ApiModelProperty(value = "施工编号")
    @Column(name = "sgbh")
     String getSgbh();
    /**
     * 永久征地
     */
    @ApiModelProperty(value = "永久征地")
    @Column(name = "yjzd")
     Double getYjzd();
    /**
     * 临时征地
     */
    @ApiModelProperty(value = "临时征地")
    @Column(name = "lszd")
     Double getLszd();
    /**
     * 工程用地时间
     */
    @ApiModelProperty(value = "工程用地时间")
     String  getGcydTime();
    /**
     * 公司控制节点
     */
    @ApiModelProperty(value = "公司控制节点")
    @Column(name = "gskzjd")
     String  getGskzjd();
    /**
     * 地方政府交地时间
     */
    @ApiModelProperty(value = "地方政府交地时间")
    @Column(name = "dfzfjdsj")
     String getDfzfjdsj();
    /**
     * 设计交图控制时间
     */
    @ApiModelProperty(value = "设计交图控制时间")
    @Column(name = "sjjtkzsj")
     String getSjjtkzsj();
    /**
     * 工期(月)
     */
    @ApiModelProperty(value = "工期(月)")
    @Column(name = "gq")
      String getGq();
    /**
     * 责任领导
     */
    @ApiModelProperty(value = "责任领导")
    @Column(name = "zrld")
     String getZrld();
    /**
     * 政府负责人
     */

    @ApiModelProperty(value = "政府负责人")
    @Column(name = "zffzr")
     String getZffzr();
    /**
     * 责任人
     */
    @ApiModelProperty(value = "责任人")
    @Column(name = "zrr")
     String getZrr();
    /**
     * 红线
     */
    @ApiModelProperty(value = "红线")
    @Column(name = "hx")
     String getHx();
    /**
     * 红线状态
     */
    @ApiModelProperty(value = "红线状态")
    @Column(name = "hxtype")
     Integer getHxtype();
    /**
     * 勘界图
     */
    @ApiModelProperty(value = "勘界图")
    @Column(name = "kjt")
     String getKjt();
    /**
     * 勘界图状态
     */
    @ApiModelProperty(value = "勘界图状态")
    @Column(name = "kjttype")
     Integer getKjttype();
    /**
     * 租地合同
     */
    @ApiModelProperty(value = "租地合同")
    @Column(name = "zdht")
     String getZdht();
    /**
     * 租地合同状态
     */
    @ApiModelProperty(value = "租地合同状态")
    @Column(name = "zdhttype")
     Integer getZdhttype();
    /**
     * 复垦方案
     */
    @ApiModelProperty(value = "复垦方案")
    @Column(name = "fkfa")
     String getFkfa();
    /**
     * 复垦方案状态
     */
    @ApiModelProperty(value = "复垦方案状态")
    @Column(name = "fkfatype")
     Integer getFkfatype();
    /**
     * 复垦保函
     */
    @ApiModelProperty(value = "复垦保函")
    @Column(name = "fkbh")
     String getFkbh();

    @ApiModelProperty(value = "规划意见")
    @Column(name = "ghyj")
     String getGhyj();

    @ApiModelProperty(value = "规划意见类型")
    @Column(name = "ghyjtype")
     Integer getGhyjtype();
    /**
     * 复垦保函状态
     */
    @ApiModelProperty(value = "复垦保函状态")
    @Column(name = "fkbhtype")
     Integer getFkbhtype();
    /**
     * 林地报告
     */
    @ApiModelProperty(value = "林地报告")
    @Column(name = "ldbg")
     String getLdbg();
    /**
     * 林地报告状态
     */
    @ApiModelProperty(value = "林地报告状态")
    @Column(name = "ldbgtype")
     Integer getLdbgtype();
    /**
     * 基本农田
     */
    @ApiModelProperty(value = "基本农田")
    @Column(name = "jbnt")
     String getJbnt();
    /**
     * 基本农田状态
     */
    @ApiModelProperty(value = "基本农田状态")
    @Column(name = "jbnttype")
     Integer getJbnttype();
    /**
     * 批复文件
     */
    @ApiModelProperty(value = "批复文件")
    @Column(name = "pfwj")
     String getPfwj();
    /**
     * 批复文件状态
     */
    @ApiModelProperty(value = "批复文件状态")
    @Column(name = "pfwjtype")
     Integer getPfwjtype();
    /**
     * 批复文件
     */
    @ApiModelProperty(value = "临时用地批复")
    @Column(name = "lsydpf")
     String getLsydpf();
    /**
     * 批复文件状态
     */
    @ApiModelProperty(value = "临时用地批复状态")
    @Column(name = "lsydpftype")
     Integer getLsydpftype();
    /**
     * 定界放桩
     */
    @ApiModelProperty(value = "定界放桩")
    @Column(name = "djfz")
     String getDjfz();
    /**
     * 定界放桩状态
     */
    @ApiModelProperty(value = "定界放桩状态")
    @Column(name = "djfztype")
     Integer getDjfztype();
    /**
     * 实物复核
     */
    @ApiModelProperty(value = "实物复核")
    @Column(name = "swfh")
     String getSwfh();
    /**
     * 实物复核状态
     */
    @ApiModelProperty(value = "实物复核状态")
    @Column(name = "swfhtype")
     Integer getSwfhtype();
    /**
     * 评估
     */
    @ApiModelProperty(value = "评估")
    @Column(name = "pg")
     String getPg();
    /**
     * 评估状态
     */
    @ApiModelProperty(value = "评估状态")
    @Column(name = "pgtype")
     Integer getPgtype();
    /**
     * 合同兑付
     */
    @ApiModelProperty(value = "合同兑付")
    @Column(name = "htdf")
     String getHtdf();
    /**
     * 合同兑付状态
     */
    @ApiModelProperty(value = "合同兑付状态")
    @Column(name = "htdftype")
     Integer getHtdftype();
    /**
     * 围闭情况
     */
    @ApiModelProperty(value = "围闭情况")
    @Column(name = "wbqk")
     String getWbqk();
    /**
     * 围闭情况状态
     */
    @ApiModelProperty(value = "围闭情况状态")
    @Column(name = "wbqktype")
     Integer getWbqktype();
    /**
     * 进场施工
     */
    @ApiModelProperty(value = "进场施工")
    @Column(name = "jcsg")
     String getJcsg();
    /**
     * 进场施工状态
     */
    @ApiModelProperty(value = "进场施工状态")
    @Column(name = "jcsgtype")
     Integer getJcsgtype();
    /**
     * 工作进展
     */
    @ApiModelProperty(value = "工作进展")
    @Column(name = "gzjz")
     String getGzjz();
    /**
     * 存在问题
     */
    @ApiModelProperty(value = "存在问题")
    @Column(name = "czwt")
     String getCzwt();
    /**
     * 解决措施
     */
    @ApiModelProperty(value = "解决措施")
    @Column(name = "jjcs")
     String getJjcs();
    /**
     * 总序
     */
    @ApiModelProperty(value = "总序")
    @Column(name = "land_order")
    Integer getLandOrder();
    /**
     * 分序
     */
    @ApiModelProperty(value = "分序")
    @Column(name = "point_order")
    Integer getPointOrder();

    @ApiModelProperty(value = "经度")
    @Column(name = "lgtd")
    Double getLgtd();


    @ApiModelProperty(value = "纬度")
    @Column(name = "lttd")
    Double getLttd();

    @ApiModelProperty(value = "全景图地址")
    @Column(name = "chart_url")
     String getChartUrl();

    @ApiModelProperty(value = "行政区地址")

    @Column(name = "merger_name")
    String getMergerName();

    /**
//     * 创建人
//     */
//    @ApiModelProperty(value = "创建用户")
//    @CreatedBy
//    @Column(name = "create_staff", nullable = false, updatable = false)
//     String createStaff();
//
//    /**
//     * 创建时间
//     */
//    @ApiModelProperty(value = "创建时间")
//    @CreatedDate
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    @Column(name = "create_time", nullable = false, updatable = false)
//     Date createTime();
//
//    /**
//     * 修改人
//     */
//
//    @ApiModelProperty(value = "修改用户")
//    @LastModifiedBy
//    @Column(name = "update_staff", nullable = false, insertable = false, updatable = true)
//     String updateStaff();
//
//    /**
//     * 修改时间
//     */
//    @ApiModelProperty(value = "创建时间")
//    @CreatedDate
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    @Column(name = "update_time", nullable = false, insertable = false, updatable = true)
//     Date updateTime();

}
