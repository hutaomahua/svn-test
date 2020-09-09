package com.lyht.business.abm.settle.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @author HuangTianhao
* @date 2019年11月28日 
*/
@ApiModel(description = "集中居民点建设实施状态表")
@Entity
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_abm_construction_settle_status")
public class SettleStatus implements Serializable{
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
     * 区分字段
     */
	@Column(name = "diff")
    @ApiModelProperty(value = "区分字段")
    private String diff;
    /**
     * 居民点名称
     */
	@Column(name = "name")
    @ApiModelProperty(value = "居民点名称")
    private String name;
    /**
     * 实施时间
     */
	@Column(name = "time_start")
    @ApiModelProperty(value = "实施时间")
    private Date timeStart;
    /**
     * 实施信息截止年月
     */
	@Column(name = "time_end")
    @ApiModelProperty(value = "实施信息截止年月")
    private Date timeEnd;
    /**
     * 实施状态
     */
	@Column(name = "built_status")
    @ApiModelProperty(value = "实施状态")
    private String builtStatus;
	/**
     * 总体形象进度
     */
	@Column(name = "status_discribe")
    @ApiModelProperty(value = "总体形象进度")
    private String statusDiscribe;
    /**
     * 进度评价
     */
	@Column(name = "status_comment")
    @ApiModelProperty(value = "进度评价")
    private String statusComment;
    /**
     * 进度滞后原因
     */
	@Column(name = "status_reason")
    @ApiModelProperty(value = "进度滞后原因")
    private String statusReason;
    /**
     * 已安置移民
     */
	@Column(name = "settled_size")
    @ApiModelProperty(value = "已安置移民")
    private int settledSize;
    /**
     * 未安置移民
     */
	@Column(name = "unsettled_size")
    @ApiModelProperty(value = "未安置移民")
    private int unsettledSize;
    /**
     * 已使用资金
     */
	@Column(name = "cur_cost")
    @ApiModelProperty(value = "已使用资金")
    private double curCost;
	
	
    /**
     * 内码
     */
	@Column(name = "nm")
    @ApiModelProperty(value = "内码")
    private String nm;
	   /**
     * 内码
     */
	@Column(name = "info_nm")
    @ApiModelProperty(value = "居民信息内码")
    private String infoNm;
	
	/**
     * 创建时间
     */

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    /**
     * 创建用户
     */
    @ApiModelProperty(value = "创建")
    @CreatedBy
    @Column(name = "create_user", nullable = false, updatable = false)
    private String createUser;


   /**
     * 修改时间
     */
    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "modify_time", nullable = false, insertable = false, updatable = true)
    private Date modifyTime;

    /**
     * 修改用户
     */
    @ApiModelProperty(value = "修改用户")
    @LastModifiedBy
    @Column(name = "modify_user", nullable = false, insertable = false, updatable = true)
    private String modifyUser;

    /**
     * remark
     */
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", nullable = false)
    private String remark;

	public String getInfoNm() {
		return infoNm;
	}

	public void setInfoNm(String infoNm) {
		this.infoNm = infoNm;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public String getBuiltStatus() {
		return builtStatus;
	}

	public String getStatusDiscribe() {
		return statusDiscribe;
	}

	public String getStatusComment() {
		return statusComment;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public int getSettledSize() {
		return settledSize;
	}

	public int getUnsettledSize() {
		return unsettledSize;
	}

	public double getCurCost() {
		return curCost;
	}

	public String getNm() {
		return nm;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public void setBuiltStatus(String builtStatus) {
		this.builtStatus = builtStatus;
	}

	public void setStatusDiscribe(String statusDiscribe) {
		this.statusDiscribe = statusDiscribe;
	}

	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public void setSettledSize(int settledSize) {
		this.settledSize = settledSize;
	}

	public void setUnsettledSize(int unsettledSize) {
		this.unsettledSize = unsettledSize;
	}

	public void setCurCost(double curCost) {
		this.curCost = curCost;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}


    
}