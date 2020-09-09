package com.lyht.business.abm.plan.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "实施方案")
@Entity
@Table(name = "t_abm_scheme")
public class AbmSchemeEntity implements Serializable {
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
     * 计划名
     */
    @ApiModelProperty(value = "内码")
    private String nm;

    /**
     * scheme_name
     */
    @ApiModelProperty(value = "方案名称")
    @Column(name = "scheme_name", nullable = false)
    private String schemeName;

    /**
     * scheme_code
     */
    @ApiModelProperty(value = "方案编码")
    @Column(name = "scheme_code", nullable = false)
    private String schemeCode;

    /**
     * authorized_unit
     */
    @ApiModelProperty(value = "编制单位")
    @Column(name = "authorized_unit", nullable = false)
    private String authorizedUnit;

    /**
     * authorized_date
     */
    @ApiModelProperty(value = "编制时间")
    @Column(name = "authorized_date", nullable = false)
    private Date authorizedDate;

    /**
     * user_nm
     */
    @ApiModelProperty(value = "用户内码")
    @Column(name = "user_nm", nullable = false)
    private String userNm;

    /**
     * uploa_date
     */
    @ApiModelProperty(value = "上传时间")
    @Column(name = "uploa_date", nullable = false)
    private Date uploaDate;

    /**
     * state
     */
    @ApiModelProperty(value = "上传时间")
    @Column(name = "state", nullable = false)
    private String state;

    /**
     * 排序序号
     */
    @ApiModelProperty(value = "排序序号")
    private Integer sort;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建用户")
    @CreatedBy
    @Column(name = "create_staff", nullable = false, updatable = false)
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;


    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改用户")
    @LastModifiedBy
    @Column(name = "update_staff", nullable = false, insertable = false, updatable = true)
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "update_time", nullable = false, insertable = false, updatable = true)
    private Date updateTime;

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

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getAuthorizedUnit() {
        return authorizedUnit;
    }

    public void setAuthorizedUnit(String authorizedUnit) {
        this.authorizedUnit = authorizedUnit;
    }

    public Date getAuthorizedDate() {
        return authorizedDate;
    }

    public void setAuthorizedDate(Date authorizedDate) {
        this.authorizedDate = authorizedDate;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public Date getUploaDate() {
        return uploaDate;
    }

    public void setUploaDate(Date uploaDate) {
        this.uploaDate = uploaDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(String createStaff) {
        this.createStaff = createStaff;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(String updateStaff) {
        this.updateStaff = updateStaff;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}