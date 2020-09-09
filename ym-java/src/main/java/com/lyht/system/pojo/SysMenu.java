package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 *作者： liuamang
 *脚本日期:2019年2月24日 22:29:43
 *说明:  系统菜单
*/
@Entity
@Table(name = "sys_menu")
@ApiModel(description = "系统菜单")
public class SysMenu implements java.io.Serializable{

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
    private List<SysMenu> children;

    /** default constructor */
    public SysMenu() {
    
    }
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
      *短编码
    */
    @Column(name = "scode" )
    public  String getScode() {
        return this.scode;
    }
    public void setScode( String scode) {
        this.scode = scode;
    }        

    /**
      *全编码
    */
    @Column(name = "fcode"  )
    public  String getFcode() {
        return this.fcode;
    }
    public void setFcode( String fcode) {
        this.fcode = fcode;
    }        

    /**
      *名称
    */
    @Column(name = "name" , nullable = false )
    public  String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }        

    @Column(name = "super_id"  )
    public  Integer getSuperId() {
        return this.superId;
    }
    public void setSuperId( Integer superId) {
        this.superId = superId;
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
      *图标
    */
    @Column(name = "menu_icon"  )
    public  String getMenuIcon() {
        return this.menuIcon;
    }
    public void setMenuIcon( String menuIcon) {
        this.menuIcon = menuIcon;
    }        

    /**
      *url
    */
    @Column(name = "menu_url"  )
    public  String getMenuUrl() {
        return this.menuUrl;
    }
    public void setMenuUrl( String menuUrl) {
        this.menuUrl = menuUrl;
    }        

    /**
      *菜单唯一标识
    */
    @Column(name = "menuflag")
    public  String getMenuflag() {
        return this.menuflag;
    }
    public void setMenuflag( String menuflag) {
        this.menuflag = menuflag;
    }        

    /**
      *是否按钮
    */
    @Column(name = "isbtn")
    public  Integer getIsbtn() {
        return this.isbtn;
    }
    public void setIsbtn( Integer isbtn) {
        this.isbtn = isbtn;
    }

    @Transient
    public List<SysMenu> getChildren() {
        return children;
    }
    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}