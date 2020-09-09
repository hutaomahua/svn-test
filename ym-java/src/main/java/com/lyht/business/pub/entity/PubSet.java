package com.lyht.business.pub.entity;

import javax.persistence.*;

/**
 *作者： liuamang
 *脚本日期:2019年2月19日 23:24:03
 *说明:  系统设置
*/
@Entity
@Table(name = "pub_set")
public class PubSet implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**
    *主键id
    */
    private Integer id;
    /**
    *唯一编号
    */
    private String nm;
    /**
    *远程同步地址
    */
    private String serviceUrl;
    /**
    *同步认证码
    */
    private String authCode;
    /**
    *系统级别
    */
    private Integer setupLevel;
    /**
    *注册单位
    */
    private String regOrganization;
    /**
    *注册截止时间
    */
    private String regUpCode;
    /**
    *授权码
    */
    private String regCode;
    /**
    *其他系统设置
    */
    private Integer otherSet;
    
    /** default constructor */
    public PubSet() {
    
    }
    /** full constructor */
    /*
    public PubSet(
                  Integer id ,
                  String nm ,
                  String serviceUrl ,
                  String authCode ,
                  Integer setupLevel ,
                  String regOrganization ,
                  String regUpCode ,
                  String regCode ,
                  Integer otherSet 
                  ) {
        super();
        this.id = id;
        this.nm = nm;
        this.serviceUrl = serviceUrl;
        this.authCode = authCode;
        this.setupLevel = setupLevel;
        this.regOrganization = regOrganization;
        this.regUpCode = regUpCode;
        this.regCode = regCode;
        this.otherSet = otherSet;
    }*/
 
    //属性get/set定义       		

    /**
      *主键id
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
      *唯一编号
    */
    @Column(name = "nm"  )
    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }        

    /**
      *远程同步地址
    */
    @Column(name = "service_url"  )
    public  String getServiceUrl() {
        return this.serviceUrl;
    }
    public void setServiceUrl( String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }        

    /**
      *同步认证码
    */
    @Column(name = "auth_code"  )
    public  String getAuthCode() {
        return this.authCode;
    }
    public void setAuthCode( String authCode) {
        this.authCode = authCode;
    }        

    /**
      *系统级别
    */
    @Column(name = "setup_level"  )
    public  Integer getSetupLevel() {
        return this.setupLevel;
    }
    public void setSetupLevel( Integer setupLevel) {
        this.setupLevel = setupLevel;
    }        

    /**
      *注册单位
    */
    @Column(name = "reg_organization"  )
    public  String getRegOrganization() {
        return this.regOrganization;
    }
    public void setRegOrganization( String regOrganization) {
        this.regOrganization = regOrganization;
    }        

    /**
      *注册截止时间
    */
    @Column(name = "reg_up_code"  )
    public  String getRegUpCode() {
        return this.regUpCode;
    }
    public void setRegUpCode( String regUpCode) {
        this.regUpCode = regUpCode;
    }        

    /**
      *授权码
    */
    @Column(name = "reg_code"  )
    public  String getRegCode() {
        return this.regCode;
    }
    public void setRegCode( String regCode) {
        this.regCode = regCode;
    }        

    /**
      *其他系统设置
    */
    @Column(name = "other_set"  )
    public  Integer getOtherSet() {
        return this.otherSet;
    }
    public void setOtherSet( Integer otherSet) {
        this.otherSet = otherSet;
    }        
    
    
}