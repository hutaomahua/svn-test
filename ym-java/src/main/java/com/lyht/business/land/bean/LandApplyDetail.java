package com.lyht.business.land.bean;

import com.alibaba.fastjson.JSON;

import java.sql.Date;

/**
 * 用地报批
 *
 * @author hxl
 */
public class LandApplyDetail {

    private Integer count;
    private Integer id;
    private String nm;
    private String applyType;
    private String land;
    private String landName;
    private String level;
    private String region;
    private String workProgress;
    private String problem;
    private String nextPlan;
    private String workContent;
    private String operStaffNm;
    private Date registerDate;
    private Integer flag;
    private String remark;
    private Date pEndDate;
    private Date rEndDate;
    private String batchName;
    private String yjfl;
    private String ejfl;
    private String sjfl;
    // 关联表字段
    private String classify;//字典分类
    private String bName;//
    private String yjname;
    private String ejname;
    private String sjname;
    private String dikuai;
    private String jindu;//进度最新
    private Integer xuhao;
    private String diming;

    private String pText;

    private String levelValue;// 字典表
    private String processStep;//报批步骤
    private String fileUrl;// 存储路径
    private String fileType;//文件类型
    private String fileName;//文件类型名
    private String operStaffName;// 人员表
    private Integer step;
    private String[] dictionaries;
    private String[] selectDimingValue;
    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getDikuai() {
        return dikuai;
    }

    public void setDikuai(String dikuai) {
        this.dikuai = dikuai;
    }

    public void setLandName(String landName) {
        this.landName = landName;
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

    public String getYjname() {
        return yjname;
    }

    public void setYjname(String yjname) {
        this.yjname = yjname;
    }

    public String getEjname() {
        return ejname;
    }

    public void setEjname(String ejname) {
        this.ejname = ejname;
    }

    public String getSjname() {
        return sjname;
    }

    public void setSjname(String sjname) {
        this.sjname = sjname;
    }

    public String getJindu() {
        return jindu;
    }

    public void setJindu(String jindu) {
        this.jindu = jindu;
    }


    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue;
    }

    public String getProcessStep() {
        return processStep;
    }

    public void setProcessStep(String processStep) {
        this.processStep = processStep;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOperStaffName() {
        return operStaffName;
    }

    public void setOperStaffName(String operStaffName) {
        this.operStaffName = operStaffName;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String[] getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(String[] dictionaries) {
        this.dictionaries = dictionaries;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String[] getSelectDimingValue() {
        return selectDimingValue;
    }

    public void setSelectDimingValue(String[] selectDimingValue) {
        this.selectDimingValue = selectDimingValue;
    }

    public Integer getXuhao() {
        return xuhao;
    }

    public void setXuhao(Integer xuhao) {
        this.xuhao = xuhao;
    }

    public String getDiming() {
        return diming;
    }

    public void setDiming(String diming) {
        this.diming = diming;
    }

    public String getpText() {
        return pText;
    }

    public void setpText(String pText) {
        this.pText = pText;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}