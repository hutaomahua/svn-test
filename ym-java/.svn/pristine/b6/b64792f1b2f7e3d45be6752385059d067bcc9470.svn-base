package com.lyht.business.info.entity;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "家庭成员")
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_family")
public class InfoFamilyEntity {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;
	
	@ApiModelProperty("项目")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty("行政区")
	@Column(name = "region")
	private String region;

	@ApiModelProperty("征地范围")
	@Column(name = "scope")
	private String scope;
	
	@ApiModelProperty("权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("姓名")
	@Column(name = "name")
	private String name;

	@ApiModelProperty("性别")
	@Column(name = "gender")
	private String gender;

	@ApiModelProperty("身份证号")
	@Column(name = "id_card")
	private String idCard;

	@ApiModelProperty("民族")
	@Column(name = "national")
	private String national;

	@ApiModelProperty("age")
	@Column(name = "age")
	private Integer age;

	@ApiModelProperty("与户主关系")
	@Column(name = "master_relationship")
	private String masterRelationship;

	@ApiModelProperty("文化程度")
	@Column(name = "education_level")
	private String educationLevel;

	@ApiModelProperty("现从事职业")
	@Column(name = "present_occupation")
	private String presentOccupation;

	@ApiModelProperty("现就读学校")
	@Column(name = "current_school")
	private String currentSchool;

	@ApiModelProperty("户口所在地")
	@Column(name = "households_home")
	private String householdsHome;

	@ApiModelProperty("户口类型")
	@Column(name = "households_type")
	private String householdsType;

	@ApiModelProperty("经度")
	@Column(name = "lgtd")
	private String lgtd;

	@ApiModelProperty("纬度")
	@Column(name = "lttd")
	private String lttd;

	@ApiModelProperty("高程")
	@Column(name = "altd")
	private String altd;

	@ApiModelProperty("图幅号")
	@Column(name = "in_map")
	private String inMap;

	@ApiModelProperty("是否搬迁安置")
	@Column(name = "is_move")
	private String isMove;

	@ApiModelProperty("是否生产安置人口")
	@Column(name = "is_produce")
	private String isProduce;

	@ApiModelProperty("复核状态")
	@Column(name = "fh_state")
	private String fhState;

	@ApiModelProperty("界定条件")
	@Column(name = "define")
	private String define;

	@ApiModelProperty("是否符合")
	@Column(name = "is_satisfy")
	private String isSatisfy;

	@ApiModelProperty("乡")
	@Column(name = "xiang")
	private String xiang;

	@ApiModelProperty("村")
	@Column(name = "cun")
	private String cun;

	@ApiModelProperty("组")
	@Column(name = "zu")
	private String zu;

	@ApiModelProperty("安置类型")
	@Column(name = "place_type")
	private String placeType;

	@ApiModelProperty("安置地点")
	@Column(name = "place_name")
	private String placeName;
	
	@ApiModelProperty("安置位置（分散安置）")
	@Column(name = "place_address")
	private String placeAddress;

	@ApiModelProperty("去向")
	@Column(name = "to_where")
	private String toWhere;

	@ApiModelProperty("搬迁公示状态")
	@Column(name = "bq_gs_state")
	private String bqGsState;

	@ApiModelProperty("数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty("阶段")
	@Column(name = "stage")
	private String stage;

	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time", updatable = false)
	private Date createTime;
	@ApiModelProperty(value = "创建用户")
	@CreatedBy
	@Column(name = "create_user", updatable = false)
	private String createUser;
	@ApiModelProperty(value = "修改时间")
	@LastModifiedDate
	@Column(name = "modify_time")
	private Date modifyTime;
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user")
	private String modifyUser;

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty("文件数量")
	@Transient
	private Integer fileCount;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}