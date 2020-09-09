package com.lyht.business.abm.plan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实物指标公示")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_abm_publicity")
public class PublicityEntity {
	 private static final long serialVersionUID = 1L;

	    /**
	     * 主键id
	     */
	    @ApiModelProperty(value = "唯一ID，修改必填")
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;

	   
	    @ApiModelProperty(value = "唯一内码 ")
	    private String nm;
	    
	    @ApiModelProperty(value = "公示编码 ")
	    private String code;
	    
	    @ApiModelProperty(value = "公示名称")
	    private String name;
	    
	    @ApiModelProperty(value = "公示类型")
	    private String type;
	    
	    @ApiModelProperty(value = "创建时间")
	    @Column(name = "create_time", nullable = false)
	    private String createTime;
	    
	    @ApiModelProperty(value = "开始时间")
	    @Column(name = "start_time", nullable = false)
	    private String startTime;
	    
	    @ApiModelProperty(value = "结束时间")
	    @Column(name = "end_time", nullable = false)
	    private String endTime;
	    
	    @ApiModelProperty(value = "公示记录")
	    @Column(name = "recor", nullable = false)
	    private String recor;
	    
	    @ApiModelProperty(value = "公示单位")
	    @Column(name = "unit", nullable = false)
	    private String unit;
	    
	    @ApiModelProperty(value = "公示说明")
	    @Column(name = "remarks", nullable = false)
	    private String remarks;
	    
	    
	    @ApiModelProperty(value = "指标类型 1 权属人 2 搬迁人口")
	    @Column(name = "zb_type", nullable = false)
	    private String zbType;
	    
	    
	    @ApiModelProperty(value = "行政区")
	    @Column(name = "region", nullable = false)
	    private String region;
	    
	    @ApiModelProperty(value = "行政区名称")
	    @Column(name = "region_name", nullable = false)
	    private String regionName;
	    
	    @ApiModelProperty(value = "操作人")
	    @Column(name = "cz_name", nullable = false)
	    private String czName;
	    
	    @ApiModelProperty(value = "操作时间")
	    @Column(name = "cz_time", nullable = false)
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	    private String czTime;
	    
	    
	    @ApiModelProperty(value = "流程ID")
	    @Column(name = "process_id")
	    private String processId;
	    
	    @ApiModelProperty(value = "公示状态 0待公式 1公示中  2公示结束")
	    @Column(name = "state")
	    private String state;
	 
	    
		public String getCode() {
			return code;
		}




		public void setCode(String code) {
			this.code = code;
		}




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




		public String getName() {
			return name;
		}




		public void setName(String name) {
			this.name = name;
		}




		public String getType() {
			return type;
		}




		public void setType(String type) {
			this.type = type;
		}




		public String getCreateTime() {
			return createTime;
		}




		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}




		




		public String getEndTime() {
			return endTime;
		}




		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}




		public String getStartTime() {
			return startTime;
		}




		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}




		public String getRecor() {
			return recor;
		}




		public void setRecor(String recor) {
			this.recor = recor;
		}




		public String getProcessId() {
			return processId;
		}




		public void setProcessId(String processId) {
			this.processId = processId;
		}




		public String getUnit() {
			return unit;
		}




		public void setUnit(String unit) {
			this.unit = unit;
		}




		public String getRemarks() {
			return remarks;
		}




		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}




		public String getZbType() {
			return zbType;
		}




		public void setZbType(String zbType) {
			this.zbType = zbType;
		}




		public String getRegion() {
			return region;
		}




		public void setRegion(String region) {
			this.region = region;
		}




		public String getCzName() {
			return czName;
		}




		public void setCzName(String czName) {
			this.czName = czName;
		}




		public String getCzTime() {
			return czTime;
		}




		public void setCzTime(String czTime) {
			this.czTime = czTime;
		}




		public String getRegionName() {
			return regionName;
		}




		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}

		



		public String getState() {
			return state;
		}




		public void setState(String state) {
			this.state = state;
		}




		@Override
	    public String toString() {
	        return JSON.toJSONString(this);
	    }

}
