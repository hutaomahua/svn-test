package com.lyht.business.abm.settle.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
* @author HuangTianhao
* @date 2019年11月29日 
*/
public class SettleVo {
    /**
     * 居民点名称
     */
    private String name;
    /**
     * 行政区
     */
    private String region;
    /**
     * 专业大类
     */
    private String regionType;
    /**
     * 安置类型
     */
    private String setttleType;
    /**
     * 实施状态
     */
    private String builtStatus;
    /**
     * 实施时间
     */
    private Date timeStart;
    /**
     * 区分字段，居民点为1
     */
    private String diff;

   
    private String modifyUser;
	public String getName() {
		return name;
	}
	public String getRegion() {
		return region;
	}
	public String getRegionType() {
		return regionType;
	}
	public String getSetttleType() {
		return setttleType;
	}

	public String getModifyUser() {
		return modifyUser;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setRegionType() {
		this.regionType = regionType;
	}
	public void setSetttleType(String setttleType) {
		this.setttleType = setttleType;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getBuiltStatus() {
		return builtStatus;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	public void setBuiltStatus(String builtStatus) {
		this.builtStatus = builtStatus;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}
	
}
