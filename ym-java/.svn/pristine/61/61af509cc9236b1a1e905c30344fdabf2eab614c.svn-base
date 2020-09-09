package com.lyht.business.land.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangAn
 * @date 2019/10/15 10:07
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="t_land_process")
public class LandPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nm")
    private String nm;

    @Column(name = "land_nm",nullable = false)
    private String landNm;

    @Column(name = "process_step",nullable = false)
    private String processStep;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @CreatedBy
    @Column(name = "create_staff_nm",nullable = false,updatable = false)
    private String createStaffNm;

    @Column(name ="p_text",nullable = false)
    private String pText;

    @Column(name ="p_state",nullable = false)
    private Integer pState;

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

    public String getLandNm() {
        return landNm;
    }

    public void setLandNm(String landNm) {
        this.landNm = landNm;
    }

    public String getProcessStep() {
        return processStep;
    }

    public void setProcessStep(String processStep) {
        this.processStep = processStep;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateStaffNm() {
        return createStaffNm;
    }

    public void setCreateStaffNm(String createStaffNm) {
        this.createStaffNm = createStaffNm;
    }

    public String getpText() {
        return pText;
    }

    public void setpText(String pText) {
        this.pText = pText;
    }

    public Integer getpState() {
        return pState;
    }

    public void setpState(Integer pState) {
        this.pState = pState;
    }
}
