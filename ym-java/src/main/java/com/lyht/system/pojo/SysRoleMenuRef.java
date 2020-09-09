package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "sys_role_menu_ref")
@ApiModel(description = "角色菜单关联表")
public class SysRoleMenuRef  implements java.io.Serializable{

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
     *菜单内码
     */
    @ApiModelProperty(value = "菜单内码")
    private String menuNm;
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
     *菜单内码
     */
    @Column(name = "menu_nm" , nullable = false )
    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
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
