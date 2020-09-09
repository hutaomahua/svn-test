package com.lyht.system.bean;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "人员信息")
public class SysStaffDetail {

	@ApiModelProperty(value = "查询字段")
	private String searchName;

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "唯一ID")
	private Integer id;
	/**
	 * 唯一编号
	 */
	@ApiModelProperty(value = "内码")
	private String nm;
	/**
	 * 人员类型
	 */
	@ApiModelProperty(value = "人员类型")
	private Integer staff_type;
	/**
	 * 人员编码
	 */
	@ApiModelProperty(value = "人员编码")
	private String staff_code;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String staff_name;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private String staff_sex;
	/**
	 * 民族
	 */
	@ApiModelProperty(value = "名族")
	private String staff_ethnic;
	/**
	 * 民族
	 */
	@ApiModelProperty(value = "名族")
	private String staffEthnicName;
	/**
	 * 生日
	 */
	@ApiModelProperty(value = "生日")
	private String staff_birthday;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String staff_link;
	/**
	 * 籍贯
	 */
	@ApiModelProperty(value = "联籍贯")
	private String staff_origin;
	/**
	 * 所属单位
	 */
	@ApiModelProperty(value = "所属单位")
	private String dept_code;
	/**
	 * 文化程度NM
	 */
	@ApiModelProperty(value = "文化程度NM")
	private String staff_education;
	/**
	 * 文化程度
	 */
	@ApiModelProperty(value = "文化程度")
	private String educationName;

	/**
	 * 职务NM
	 */
	@ApiModelProperty(value = "职务NM")
	private String staff_position;

	/**
	 * 职务
	 */
	@ApiModelProperty(value = "职务")
	private String positionName;
	/**
	 * 排序号
	 */
	@ApiModelProperty(value = "排序号")
	private Integer sort_num;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer state;

	private String roleName;

	private String runlogNumber;
	private String uuid;

	public String getRunlogNumber() {
		return runlogNumber;
	}

	public void setRunlogNumber(String runlogNumber) {
		this.runlogNumber = runlogNumber;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public Integer getStaff_type() {
		return staff_type;
	}

	public void setStaff_type(Integer staff_type) {
		this.staff_type = staff_type;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStaff_sex() {
		return staff_sex;
	}

	public void setStaff_sex(String staff_sex) {
		this.staff_sex = staff_sex;
	}

	public String getStaff_ethnic() {
		return staff_ethnic;
	}

	public void setStaff_ethnic(String staff_ethnic) {
		this.staff_ethnic = staff_ethnic;
	}

	public String getStaff_birthday() {
		return staff_birthday;
	}

	public void setStaff_birthday(String staff_birthday) {
		this.staff_birthday = staff_birthday;
	}

	public String getStaff_link() {
		return staff_link;
	}

	public void setStaff_link(String staff_link) {
		this.staff_link = staff_link;
	}

	public String getStaff_origin() {
		return staff_origin;
	}

	public void setStaff_origin(String staff_origin) {
		this.staff_origin = staff_origin;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public Integer getSort_num() {
		return sort_num;
	}

	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getStaff_education() {
		return staff_education;
	}

	public void setStaff_education(String staff_education) {
		this.staff_education = staff_education;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getStaff_position() {
		return staff_position;
	}

	public void setStaff_position(String staff_position) {
		this.staff_position = staff_position;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getStaffEthnicName() {
		return staffEthnicName;
	}

	public void setStaffEthnicName(String staffEthnicName) {
		this.staffEthnicName = staffEthnicName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
