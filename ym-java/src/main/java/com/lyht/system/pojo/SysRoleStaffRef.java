package com.lyht.system.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "sys_role_staff_ref")
@ApiModel(description = "角色人员关联表")
public class SysRoleStaffRef implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 唯一编码
	 */
	private String code;
	/**
	 * 角色内码
	 */
	@ApiModelProperty(value = "角色内码")
	private String roleNm;

	/**
	 * 人员内码
	 */
	@ApiModelProperty(value = "人员内码")
	private String staffNm;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer flag;

	@ApiModelProperty(value = "流程用--uuid")
	@Column(name = "uuid")
	private String uuid;

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
	 * 唯一编码
	 */
	@Column(name = "code", nullable = false)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 人员内码
	 */
	@Column(name = "staff_nm", nullable = false)
	public String getStaffNm() {
		return staffNm;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}

	/**
	 * 状态
	 */
	@Column(name = "flag", nullable = false)
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
