package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "sys_role_partition_ref")
@ApiModel(description = "角色区域关联表")
public class SysRolePartitionRef implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    private Integer id;
    /**
     *唯一编码
     */
    private String code;
    /**
     *角色内码
     */
    @ApiModelProperty(value = "角色内码")
    private String roleNm;

    /**
     *区域内吗
     */
    @ApiModelProperty(value = "区域内吗")
    private String partitionNm;
    /**
     *状态
     */
    @ApiModelProperty(value = "状态")
    private Integer flag;

    //属性get/set定义

    /**
     *主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    public  Integer getId() {
        return this.id;
    }
    public void setId( Integer id) {
        this.id = id;
    }

    /**
     *角色内码
     */
    @Column(name = "role_nm" , nullable = false )
    public String getRoleNm() {
        return roleNm;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    /**
     *唯一编码
     */
    @Column(name = "code" , nullable = false )
    public  String getCode() {
        return this.code;
    }
    public void setCode( String code) {
        this.code = code;
    }

    /**
     *区域内码
     */
    @Column(name = "partition_nm" , nullable = false )
    public String getPartitionNm() {
        return partitionNm;
    }

    public void setPartitionNm(String partitionNm) {
        this.partitionNm = partitionNm;
    }


    /**
     *状态
     */
    @Column(name = "flag" , nullable = false )
    public  Integer getFlag() {
        return this.flag;
    }
    public void setFlag( Integer flag) {
        this.flag = flag;
    }

}
