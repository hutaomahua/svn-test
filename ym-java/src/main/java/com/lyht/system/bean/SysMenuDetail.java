package com.lyht.system.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 *作者： liuamang
 *脚本日期:2019年2月24日 22:29:43
 *说明:  系统菜单
*/
@ApiModel(description = "系统菜单")
public class SysMenuDetail implements java.io.Serializable{

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
    *短编码
    */
    @ApiModelProperty(value = "短编码")
    private String scode;
    /**
    *全编码
    */
    @ApiModelProperty(value = "长编码")
    private String fcode;
    /**
    *名称
    */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父级菜单ID")
    private Integer superId;
    /**
    *状态
    */
    @ApiModelProperty(value = "菜单状态")
    private Integer flag;
    /**
    *图标
    */
    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;
    /**
    *url
    */
    @ApiModelProperty(value = "菜单路径")
    private String menuUrl;
    /**
    *菜单唯一标识
    */
    private String menuflag;
    /**
    *是否按钮
    */
    @ApiModelProperty(value = "是否")
    private Integer isbtn;

    @OneToMany(cascade={CascadeType.ALL})
    private List<SysMenuDetail> children;

    private String super_name;

    /** default constructor */
    public SysMenuDetail() {
    
    }
    //属性get/set定义       		

    /**
      *主键
    */
    public  Integer getId() {
        return this.id;
    }
    public void setId( Integer id) {
        this.id = id;
    }        

    /**
      *内码
    */
    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }        

    /**
      *短编码
    */
    public  String getScode() {
        return this.scode;
    }
    public void setScode( String scode) {
        this.scode = scode;
    }        

    /**
      *全编码
    */
    public  String getFcode() {
        return this.fcode;
    }
    public void setFcode( String fcode) {
        this.fcode = fcode;
    }        

    /**
      *名称
    */
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    public  Integer getSuperId() {
        return this.superId;
    }
    public void setSuperId( Integer superId) {
        this.superId = superId;
    }        

    /**
      *状态
    */
    public  Integer getFlag() {
        return this.flag;
    }
    public void setFlag( Integer flag) {
        this.flag = flag;
    }        

    /**
      *图标
    */
    public  String getMenuIcon() {
        return this.menuIcon;
    }
    public void setMenuIcon( String menuIcon) {
        this.menuIcon = menuIcon;
    }        

    /**
      *url
    */
    public  String getMenuUrl() {
        return this.menuUrl;
    }
    public void setMenuUrl( String menuUrl) {
        this.menuUrl = menuUrl;
    }        

    /**
      *菜单唯一标识
    */
    public  String getMenuflag() {
        return this.menuflag;
    }
    public void setMenuflag( String menuflag) {
        this.menuflag = menuflag;
    }        

    /**
      *是否按钮
    */
    public  Integer getIsbtn() {
        return this.isbtn;
    }
    public void setIsbtn( Integer isbtn) {
        this.isbtn = isbtn;
    }

    public String getSuper_name() {
        return super_name;
    }

    public void setSuper_name(String super_name) {
        this.super_name = super_name;
    }

    @Transient
    public List<SysMenuDetail> getChildren() {
        return children;
    }
    public void setChildren(List<SysMenuDetail> children) {
        this.children = children;
    }
}