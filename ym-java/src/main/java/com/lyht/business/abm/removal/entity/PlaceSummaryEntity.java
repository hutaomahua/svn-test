package com.lyht.business.abm.removal.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(description = "安置人口汇总表")
@Entity
@Table(name = "t_abm_place_summary")
public class PlaceSummaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * nm
     */
    @ApiModelProperty(value = "唯一内码，修改必填")
    private String nm;

    /**
     * county
     */
    @ApiModelProperty(value = "县")
    private String county;

    /**
     * country
     */
    @ApiModelProperty(value = "乡")
    private String country;

    /**
     * village
     */
    @ApiModelProperty(value = "村")
    private String village;

    /**
     * group
     */
    @ApiModelProperty(value = "组")
    private String group;

    /**
     * nature
     */
    @ApiModelProperty(value = "性质")
    private String nature;

    /**
     * flood_benchmark_num
     */

    @ApiModelProperty(value = "基准年人口(淹没区)")
    @Column(name = "flood_benchmark_num", nullable = false)
    private Integer floodBenchmarkNum;

    /**
     * flood_plan_num
     */
    @ApiModelProperty(value = "规划水平年人口(淹没区)")
    @Column(name = "flood_plan_num", nullable = false)
    private Integer floodPlanNum;

    /**
     * hinge_benchmark_num
     */
    @ApiModelProperty(value = "基准年人口(枢纽建设)")
    @Column(name = "hinge_benchmark_num", nullable = false)
    private Integer hingeBenchmarkNum;

    /**
     * hinge_plan_num
     */
    @ApiModelProperty(value = "规划水平年人口(枢纽建设)")
    @Column(name = "hinge_plan_num", nullable = false)
    private Integer hingePlanNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Integer getFloodBenchmarkNum() {
        return floodBenchmarkNum;
    }

    public void setFloodBenchmarkNum(Integer floodBenchmarkNum) {
        this.floodBenchmarkNum = floodBenchmarkNum;
    }

    public Integer getFloodPlanNum() {
        return floodPlanNum;
    }

    public void setFloodPlanNum(Integer floodPlanNum) {
        this.floodPlanNum = floodPlanNum;
    }

    public Integer getHingeBenchmarkNum() {
        return hingeBenchmarkNum;
    }

    public void setHingeBenchmarkNum(Integer hingeBenchmarkNum) {
        this.hingeBenchmarkNum = hingeBenchmarkNum;
    }

    public Integer getHingePlanNum() {
        return hingePlanNum;
    }

    public void setHingePlanNum(Integer hingePlanNum) {
        this.hingePlanNum = hingePlanNum;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
