package com.lyht.business.land.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangAn
 * @date 2019/10/15 10:01
 */
//land_problem
@Entity
@Table(name = "t_land_problem")
@ApiModel(description = "用地报批问题日志")
public class LandProblem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nm")
    private String nm;

    @Column(name = "land_nm")
    private String landNm;

    @Column(name = "p_text")
    private String pText;

    @Column(name = "create_staff_nm")
    private String createStaffNm;

    @Column(name = "create_time")
    private Date createTime;

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

    public String getpText() {
        return pText;
    }

    public void setpText(String pText) {
        this.pText = pText;
    }

    public String getCreateStaffNm() {
        return createStaffNm;
    }

    public void setCreateStaffNm(String createStaffNm) {
        this.createStaffNm = createStaffNm;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
