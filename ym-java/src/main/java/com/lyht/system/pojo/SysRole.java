package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 *作者： liuamang
 *脚本日期:2019年2月18日 15:44:30
 *说明:  系统角色
*/
@Entity
@Table(name = "sys_role")
@ApiModel(description = "系统角色")
public class SysRole implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**
    *主键
    */
    private Integer id;
    /**
    *内码
    */
    private String nm;
    /**
    *角色编码
    */
    @ApiModelProperty(value = "用户编码")
    private String code;
    /**
    *角色名称
    */

    @ApiModelProperty(value = "角色名称")
    private String name;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String memo;
    /**
    *状态
    */
    @ApiModelProperty(value = "状态")
    private Integer flag;
    /**
    *系统内置
    */
    private Integer sysflag;
    /**
     *流程使用
     */
    private String uuid;
    
    /** default constructor */
    public SysRole() {
    
    }
    /** full constructor */
    /*
    public SysRole(
                  Integer id ,
                  String nm ,
                  String code ,
                  String name ,
                  String memo ,
                  Integer flag ,
                  Integer sysflag 
                  ) {
        super();
        this.id = id;
        this.nm = nm;
        this.code = code;
        this.name = name;
        this.memo = memo;
        this.flag = flag;
        this.sysflag = sysflag;
    }*/
 
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
      *内码
    */
    @Column(name = "nm" , nullable = false )
    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }        

    /**
      *角色编码
    */
    @Column(name = "code" , nullable = false )
    public  String getCode() {
        return this.code;
    }
    public void setCode( String code) {
        this.code = code;
    }        

    /**
      *角色名称
    */
    @Column(name = "name" , nullable = false )
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    /**
      *备注
    */
    @Column(name = "memo"  )
    public  String getMemo() {
        return this.memo;
    }
    public void setMemo( String memo) {
        this.memo = memo;
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

    /**
      *系统内置
    */
    @Column(name = "sysflag"  )
    public  Integer getSysflag() {
        return this.sysflag;
    }
    public void setSysflag( Integer sysflag) {
        this.sysflag = sysflag;
    }

    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}