package com.lyht.system.bean;

import java.util.List;

/**
 * @author HuangAn
 * @date 2019/9/23 10:57
 */
public class SysDeptTree {
    private  String key;
    private  String nm;
    private  String value;
    private  String title;
    private  String fCode;
    private String SuperId;
    private String deptType;
    private List<SysDeptTree> children;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getfCode() {
        return fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getSuperId() {
        return SuperId;
    }

    public void setSuperId(String superId) {
        SuperId = superId;
    }

    public List<SysDeptTree> getChildren() {
        return children;
    }

    public void setChildren(List<SysDeptTree> children) {
        this.children = children;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }
}
