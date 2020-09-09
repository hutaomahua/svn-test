package com.lyht.business.abm.removal.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@ApiModel(description = " 实施阶段人口管理表")
@Data
@Entity
@Table(name = "t_info_family_impl")
public class AbmFamilyEntity  {

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 内码
	 */
	@ApiModelProperty(value = "内码")
	private String nm;

	/**
	 * 行政区
	 */
	@ApiModelProperty(value = "行政区")
	private String region;

	/**
	 * 征地范围
	 */
	@ApiModelProperty(value = "征地范围")
	private String scope;

	/**
	 * 权属人编码
	 */
	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private String gender;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	@Column(name = "id_card")
	private String idCard;

	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族")
	private String national;

	/**
	 * age
	 */
	@ApiModelProperty(value = "年龄")
	private Integer age;

	/**
	 * 与户主关系
	 */
	@ApiModelProperty(value = "与户主关系")
	@Column(name = "master_relationship")
	private String masterRelationship;

	/**
	 * 文化程度
	 */
	@ApiModelProperty(value = "文化程度")
	@Column(name = "education_level")
	private String educationLevel;

	/**
	 * 现从事职业
	 */
	@ApiModelProperty(value = "现从事职业")
	@Column(name = "present_occupation")
	private String presentOccupation;

	/**
	 * 现就读学校
	 */
	@ApiModelProperty(value = "现就读学校")
	@Column(name = "current_school")
	private String currentSchool;

	/**
	 * 户口所在地
	 */
	@ApiModelProperty(value = "户口所在地")
	@Column(name = "households_home")
	private String householdsHome;

	/**
	 * 户口类型
	 */
	@ApiModelProperty(value = "户口类型")
	@Column(name = "households_type")
	private String householdsType;

	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private String lgtd;

	/**
	 * 纬度
	 */
	@ApiModelProperty(value = "纬度")
	private String lttd;

	/**
	 * 高程
	 */
	@ApiModelProperty(value = "高程")
	private String altd;

	/**
	 * 图幅号
	 */
	@ApiModelProperty(value = "图幅号")
	@Column(name = "in_map")
	private String inMap;

	/**
	 * 数据状态
	 */
	@ApiModelProperty(value = "数据状态")
	private String status;

	/**
	 * 创建时间
	 */

	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", updatable = false)
	private Date createTime;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建")
	@CreatedBy
	@Column(name = "create_user", updatable = false)
	private String createUser;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "modify_time", insertable = false, updatable = true)
	private Date modifyTime;

	/**
	 * 修改用户
	 */
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user", insertable = false, updatable = true)
	private String modifyUser;

	/**
	 * remark
	 */
	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	/**
	 * is_move
	 */
	@ApiModelProperty(value = "是否搬迁")
	@Column(name = "is_move")
	private String isMove;

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段")
	@Column(name = "stage")
	private String stage;

	/**
	 * 复核申请
	 */
	@ApiModelProperty(value = "复核申请")
	@Column(name = "fh_state")
	private String fhState;

	@ApiModelProperty(value = "通过的界定条件")
	@Column(name = "define")
	private String define;

	@ApiModelProperty(value = "是否符合") // 0待界定 1不通过 2通过 3界定中
	@Column(name = "is_satisfy")
	private String isSatisfy;

	@ApiModelProperty(value = "县")
	@Column(name = "xiang")
	private String xiang;

	@ApiModelProperty(value = "村")
	@Column(name = "cun")
	private String cun;

	@ApiModelProperty(value = "组")
	@Column(name = "zu")
	private String zu;

	@ApiModelProperty(value = "安置类型")
	@Column(name = "place_type")
	private String placeType;

	@ApiModelProperty(value = "安置地点")
	@Column(name = "place_name")
	private String placeName;
	
	@ApiModelProperty(value = "安置位置（分散安置）")
	@Column(name = "place_address")
	private String placeAddress;

	@ApiModelProperty(value = "去向")
	@Column(name = "to_where")
	private String toWhere;

	@ApiModelProperty(value = "公示状态 移民人口")
	@Column(name = "gs_state")
	private String gsState;

	@ApiModelProperty(value = "生产安置去向")
	@Column(name = "produce_place")
	private String producePlace;

	@ApiModelProperty(value = "移民人口新增原由 字典项")
	@Column(name = "change_add_reason")
	private String changeAddReason;
	
	@ApiModelProperty(value = "指标类型（农村、集镇）")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty(value = "文件数量")
	@Transient
	private Integer fileCount;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
