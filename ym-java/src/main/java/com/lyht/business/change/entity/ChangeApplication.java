package com.lyht.business.change.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangAn
 * @date 2019/12/5 14:24
 */
@ApiModel(value = "变更申请表")
@Entity
@Table(name = "t_change_application")
@EntityListeners(AuditingEntityListener.class)
public class ChangeApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "唯一nm")
    private String nm;

    @ApiModelProperty(value = "行政区")
    @Column(name = "region")
    private String region;

    @ApiModelProperty(value = "水利水电工程名称")
    @Column(name = "project_name")
    private String projectName;

    @ApiModelProperty(value = "设计变更项目")
    @Column(name = "change_project")
    private String changeProject;

    @ApiModelProperty(value = "概况")
    @Column(name = "general_situation")
    private String generalSituation;

    @ApiModelProperty(value = "设计变更必要性")
    @Column(name = "change_need")
    private String changeNeed;

    @ApiModelProperty(value = "设计变更主要情况")
    @Column(name = "main_case")
    private String mainCase;

    @ApiModelProperty(value = "技术经济分析")
    @Column(name = "economic_analysis")
    private String economicAnalysis;

    @ApiModelProperty(value = "所属变更类型")
    @Column(name = "change_type")
    private Integer changeType;
    @ApiModelProperty(value = "所属变更申请类型")
    @Column(name = "change_request_type")
    private Integer changeRequestType;
    @ApiModelProperty(value = "所属变更申请状态")
    @Column(name = "change_status")
    private Integer changeStatus;

    @ApiModelProperty(value = "设计变更内容关联")
    @Column(name = "design_content_nm")
    private String designContentNm;

    @ApiModelProperty(value = "申请人")
    @Column(name = "applicant")
    @CreatedBy
    private String applicant;
    @ApiModelProperty(value = "申请时间")
    @Column(name = "apply_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    
    @ApiModelProperty(value = "流程ID")
    @Column(name = "process_id")
    private String processId;
    
    public Integer getId() {
        return id;
    }

    public String getNm() {
        return nm;
    }

    public String getRegion() {
        return region;
    }

    public String getChangeProject() {
        return changeProject;
    }

    public String getGeneralSituation() {
        return generalSituation;
    }

    public String getChangeNeed() {
        return changeNeed;
    }

    public String getMainCase() {
        return mainCase;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getApplicant() {
        return applicant;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public String getEconomicAnalysis() {
        return economicAnalysis;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public Integer getChangeRequestType() {
        return changeRequestType;
    }

    public String getDesignContentNm() {
        return designContentNm;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public void setChangeRequestType(Integer changeRequestType) {
        this.changeRequestType = changeRequestType;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setChangeProject(String changeProject) {
        this.changeProject = changeProject;
    }

    public void setGeneralSituation(String generalSituation) {
        this.generalSituation = generalSituation;
    }

    public void setChangeNeed(String changeNeed) {
        this.changeNeed = changeNeed;
    }

    public void setMainCase(String mainCase) {
        this.mainCase = mainCase;
    }

    public void setEconomicAnalysis(String economicAnalysis) {
        this.economicAnalysis = economicAnalysis;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
    }

    public void setDesignContentNm(String designContentNm) {
        this.designContentNm = designContentNm;
    }

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}


}
