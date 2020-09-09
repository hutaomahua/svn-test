package com.lyht.business.rests.bean;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class LetterManagerDetail {
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue

    private int id;
    @ApiModelProperty(value = "唯一内码，修改必填")
    private String nm;
    @ApiModelProperty(value = "信访事项")
    private String matter;
    @ApiModelProperty(value = "来访人")
    private String name;
    @ApiModelProperty(value = "来访人性别")

    private String sexNm;
    @ApiModelProperty(value = "来访人职位")
    private String jobNm;
    @ApiModelProperty(value = "来访人号码")
    private String phone;
    @ApiModelProperty(value = "来访人单位或地址")
    private String dwdz;
    @ApiModelProperty(value = "所属行政区")
    private String region;
    @ApiModelProperty(value = "建议内容")
    private String content;
    private String receptionName;
    private String createTime;
    private String sex;
    private String parameter;

    /**
     * 关联字典
     */
    private String diming;

    private String job;
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDiming() {
        return diming;
    }

    public void setDiming(String diming) {
        this.diming = diming;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
