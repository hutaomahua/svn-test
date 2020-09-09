package com.lyht.business.rests.pojo;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 信访管理
 * 2019/8/12
 *
 * @author jms
 */
@ApiModel(description = "信访管理")
@Entity
@Table(name = "t_rests_letter")
public class LetterManagerEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ApiModelProperty(value = "唯一内码，修改必填")
    private String nm;
    @ApiModelProperty(value = "信访事项")
    private String matter;
    @ApiModelProperty(value = "来访人")
    private String name;
    @ApiModelProperty(value = "来访人性别")
    @Column(name = "sex_nm",nullable = false)
    private String sexNm;
    @ApiModelProperty(value = "来访人职位")
    @Column(name = "job_nm",nullable = false)
    private String jobNm;
    @ApiModelProperty(value = "来访人号码")
    private String phone;
    @ApiModelProperty(value = "来访人单位或地址")
    private String dwdz;
    @ApiModelProperty(value = "所属行政区")
    private String region;
    @ApiModelProperty(value = "建议内容")
    private String content;
    @ApiModelProperty(value = "接待人登记人")
    @Column(name = "reception_name",nullable = false)
    private String receptionName;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "来访时间")
    @Column(name = "create_time",nullable = false)
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

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexNm() {
        return sexNm;
    }

    public void setSexNm(String sexNm) {
        this.sexNm = sexNm;
    }

    public String getJobNm() {
        return jobNm;
    }

    public void setJobNm(String jobNm) {
        this.jobNm = jobNm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDwdz() {
        return dwdz;
    }

    public void setDwdz(String dwdz) {
        this.dwdz = dwdz;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceptionName() {
        return receptionName;
    }

    public void setReceptionName(String receptionName) {
        this.receptionName = receptionName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
