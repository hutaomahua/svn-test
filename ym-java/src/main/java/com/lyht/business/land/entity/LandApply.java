package com.lyht.business.land.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangAn
 * @date 2019/10/15 9:27
 */
@Entity
@Table(name = "t_land_apply")
@ApiModel(description = "用地报批")
public class LandApply implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    /**
     *内码
     */
    @Column(name = "nm", nullable = false)
    private String nm;
    /**
     * 序号
     */
    @Column(name = "xuhao")
    private Integer xuhao;
    /**
     * 报批类型
     */
    @Column(name = "apply_type")
    private String applyType;
    /**
     * 地块编号
     */
    @Column(name = "land")
    private String land;
    /**
     * 地块名称
     */
    @Column(name = "land_name")
    private String landName;
    /**
     * 归属批次名称
     */
    @Column(name = "batch_name")
    private String batchName;
    /**
     * 一级分类
     */
    @Column(name = "yjfl")
    private String yjfl;
    /**
     * 二级分类
     */
    @Column(name = "ejfl")
    private String ejfl;
    /**
     * 三级分类
     */
    @Column(name = "sjfl")
    private String sjfl;
    /**
     * 级别
     */
    @Column(name = "level")
    private String level;
    /**
     * 行政区
     */
    @Column(name = "region")
    private String region;
    /**
     * 工作进展
     */
    @Column(name = "work_progress")
    private String workProgress;
    /**
     * 存在问题
     */
    @Column(name = "problem")
    private String problem;
    /**
     * 实际完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "p_end_date")
    private Date pEndDate;
    /**
     * 计划完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "r_end_date")
    private Date rEndDate;
    /**
     * 下一步计划
     */
    @Column(name = "next_plan")
    private String nextPlan;
    /**
     * 工作内容
     */
    @Column(name = "work_content")
    private String workContent;
    /**
     * 责任人
     */
    @Column(name = "oper_staff_nm")
    private String operStaffNm;
    /**
     * 登记日期
     */
    @Column(name = "register_date")
    private Date registerDate;
    /**
     * 状态
     */
    @Column(name = "flag")
    private Integer flag;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 阶段
     */
    @Column(name = "stage")
    private String stage;

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

    public Integer getXuhao() {
        return xuhao;
    }

    public void setXuhao(Integer xuhao) {
        this.xuhao = xuhao;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getYjfl() {
        return yjfl;
    }

    public void setYjfl(String yjfl) {
        this.yjfl = yjfl;
    }

    public String getEjfl() {
        return ejfl;
    }

    public void setEjfl(String ejfl) {
        this.ejfl = ejfl;
    }

    public String getSjfl() {
        return sjfl;
    }

    public void setSjfl(String sjfl) {
        this.sjfl = sjfl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWorkProgress() {
        return workProgress;
    }

    public void setWorkProgress(String workProgress) {
        this.workProgress = workProgress;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Date getpEndDate() {
        return pEndDate;
    }

    public void setpEndDate(Date pEndDate) {
        this.pEndDate = pEndDate;
    }

    public Date getrEndDate() {
        return rEndDate;
    }

    public void setrEndDate(Date rEndDate) {
        this.rEndDate = rEndDate;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getOperStaffNm() {
        return operStaffNm;
    }

    public void setOperStaffNm(String operStaffNm) {
        this.operStaffNm = operStaffNm;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
