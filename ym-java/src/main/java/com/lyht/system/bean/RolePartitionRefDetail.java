package com.lyht.system.bean;

/**
 * @author HuangAn
 * @date 2019/9/26 14:21
 */
public class RolePartitionRefDetail {

    private Integer id;
    private String code;
    private String role_nm;
    private String partition_nm;
    private String flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRole_nm() {
        return role_nm;
    }

    public void setRole_nm(String role_nm) {
        this.role_nm = role_nm;
    }

    public String getPartition_nm() {
        return partition_nm;
    }

    public void setPartition_nm(String partition_nm) {
        this.partition_nm = partition_nm;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
