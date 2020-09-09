package com.lyht.business.abm.plan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "权属人复核申请")
@Entity
@Table(name = "t_abm_owner_verify")
public class OwnerVerifyEntity implements Serializable {
	 private static final long serialVersionUID = 1L;
	 	   
	    @ApiModelProperty(value = "唯一ID，修改必填")
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;
	    
	    @ApiModelProperty(value = "内码")
	    private String nm;
	    
	    @ApiModelProperty(value = "申请人")
	    @Column(name = "name", nullable = false)
	    private String name;
	    
	    @ApiModelProperty(value = "申请编号")
	    @Column(name = "code", nullable = false)
	    private String code;
	    
	    @ApiModelProperty(value = "地址")
	    @Column(name = "addr", nullable = false)
	    private String addr;
	    
	    @ApiModelProperty(value = "复核项目")
	    @Column(name = "verify_project", nullable = false)
	    private String verifyProject;
	    
	    @ApiModelProperty(value = "指标性质")
	    @Column(name = "nature", nullable = false)
	    private String nature;
	    
	    @ApiModelProperty(value = "政府意见")
	    @Column(name = "zf_opinion", nullable = false)
	    private String zfOpinion;
	    
	    @ApiModelProperty(value = "县移民局意见")
	    @Column(name = "ymj_opinion", nullable = false)
	    private String ymjOpinion;
	    
	   
	    
	    @ApiModelProperty(value = "复核内容")
	    @Column(name = "remark", nullable = false)
	    private String remark;
	    
	    @ApiModelProperty(value = "流程ID")
	    @Column(name = "process_id")
	    private String processId;
	    
	    @ApiModelProperty(value = "身份证")
	    @Column(name = "id_card", nullable = false)
	    private String idCard;
	    
	    @ApiModelProperty(value = "户主姓名")
	    @Column(name = "home_name", nullable = false)
	    private String homeName;
	    
		public String getProcessId() {
			return processId;
		}

		public void setProcessId(String processId) {
			this.processId = processId;
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

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getVerifyProject() {
			return verifyProject;
		}

		public void setVerifyProject(String verifyProject) {
			this.verifyProject = verifyProject;
		}

		public String getNature() {
			return nature;
		}

		public void setNature(String nature) {
			this.nature = nature;
		}

		public String getZfOpinion() {
			return zfOpinion;
		}

		public void setZfOpinion(String zfOpinion) {
			this.zfOpinion = zfOpinion;
		}

		public String getYmjOpinion() {
			return ymjOpinion;
		}

		public void setYmjOpinion(String ymjOpinion) {
			this.ymjOpinion = ymjOpinion;
		}

		

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getIdCard() {
			return idCard;
		}

		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}

		public String getHomeName() {
			return homeName;
		}

		public void setHomeName(String homeName) {
			this.homeName = homeName;
		}

	    
	    

}
