package com.lyht.system.pojo;

import javax.persistence.*;

/**
 * 作者： liuamang 脚本日期:2019年2月18日 15:44:30 说明: 数据权限配置
 */
@Entity
@Table(name = "sys_staff_role_data")
public class SysStaffRoleData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 唯一编码
	 */
	private String nm;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 人员内码
	 */
	private String staffNm;
	/**
	 * 角色内码
	 */
	private String roleNm;
	/**
	 * 菜单内码
	 */
	private String menuflag;
	/**
	 * 状态
	 */
	private String flag;

	/** default constructor */
	public SysStaffRoleData() {

	}
	/** full constructor */
	/*
	 * public SysRole( Integer id , String nm , String code , String name ,
	 * String memo , Integer flag , Integer sysflag ) { super(); this.id = id;
	 * this.nm = nm; this.code = code; this.name = name; this.memo = memo;
	 * this.flag = flag; this.sysflag = sysflag; }
	 */

	// 属性get/set定义

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 唯一编码
	 */
	@Column(name = "nm", nullable = false)
	public String getNm() {
		return this.nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}
	/**
	 * 名称
	 */
	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 人员内码
	 */
	@Column(name = "staff_nm", nullable = false)
	public String getStaffNm() {
		return this.staffNm;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}
	/**
	 * 角色内码
	 */
	@Column(name = "role_nm", nullable = false)
	public String getRoleNm() {
		return roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}
	/**
	 *菜单内码
	 */
	@Column(name = "menuflag", nullable = false)
	public String getMenuflag() {
		return menuflag;
	}

	public void setMenuflag(String menuflag) {
		this.menuflag = menuflag;
	}
	/**
	 * 状态
	 */
	@Column(name = "flag", nullable = false)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


}