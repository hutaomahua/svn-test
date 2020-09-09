package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

/**
 *作者： liuamang
 *脚本日期:2019年2月18日 23:22:44
 *说明:  账户信息
*/
@Entity
@Table(name = "sys_acct")
@ApiModel(description = "账户")
@ToString
public class SysAcct implements java.io.Serializable{

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
     *人员唯一编码 
     */


    private String staffNm;
    /**
    *账户名称
    */
    @ApiModelProperty(value = "账户名,登录必填")
    private String name;
    /**
    *账户密码
    */
    @ApiModelProperty(value = "密码,登录必填")
    private String pwd;
    /**
    *状态
    */
    private Integer flag;
    /**
    *有效期
    */
    private Date yxq;
    /**
    *系统内置
    */
    private Integer sysflag;
    
    /** default constructor */
    public SysAcct() {
    
    }
    /** full constructor */
    /*
    public SysAcct(
                  Integer id ,
                  String nm ,
                  String name ,
                  String pwd ,
                  Integer flag ,
                  String yxq ,
                  Integer sysflag 
                  ) {
        super();
        this.id = id;
        this.nm = nm;
        this.name = name;
        this.pwd = pwd;
        this.flag = flag;
        this.yxq = yxq;
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
      *账户名称
    */
    @Column(name = "name" )
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    /**
      *账户密码
    */
    @Column(name = "pwd"  )
    public  String getPwd() {
        return this.pwd;
    }
    public void setPwd( String pwd) {
        this.pwd = pwd;
    }        

    /**
      *状态
    */
    @Column(name = "flag" )
    public  Integer getFlag() {
        return this.flag;
    }
    public void setFlag( Integer flag) {
        this.flag = flag;
    }        

    /**
      *有效期
    */
    @Column(name = "yxq"  )
    public  Date getYxq() {
        return this.yxq;
    }
    public void setYxq( Date yxq) {
        this.yxq = yxq;
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
    
    @Column(name = "staff_nm"  )
	public String getStaffNm() {
		return staffNm;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}        
    
    
}