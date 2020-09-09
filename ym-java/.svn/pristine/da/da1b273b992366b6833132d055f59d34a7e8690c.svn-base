package com.lyht.business.abm.removal.entity;

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

@ApiModel(description = "权属人")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_owner_impl")
public class AbmOwnerEntity {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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

	@ApiModelProperty("年龄")
	@Column(name = "age")
	private Integer age;

	@ApiModelProperty("移民户")
	@Column(name = "immigrant")
	private Integer immigrant;

	@ApiModelProperty("农业人口")
	@Column(name = "ap")
	private Integer ap;

	@ApiModelProperty("非农业人口")
	@Column(name = "non_ap")
	private Integer nonAp;

	@ApiModelProperty("移民人口")
	@Column(name = "i_population")
	private Integer iPopulation;

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

	@ApiModelProperty("阶段")
	@Column(name = "stage")
	private String stage;

	@ApiModelProperty("指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty("权属性质")
	@Column(name = "nature")
	private String nature;

	@ApiModelProperty("权属人类型")
	@Column(name = "owner_type")
	private String ownerType;

	@ApiModelProperty("档案编号")
	@Column(name = "dos_number")
	private String dosNumber;

	@ApiModelProperty("流程id")
	@Column(name = "process_id")
	private String processId;

	@ApiModelProperty("复核状态")
	@Column(name = "fh_state")
	private String fhState;

	@ApiModelProperty("村")
	@Column(name = "cun")
	private String cun;

	@ApiModelProperty("乡")
	@Column(name = "xiang")
	private String xiang;

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

	@ApiModelProperty(value = "通过的界定条件")
	@Column(name = "define")
	private String define;

	@ApiModelProperty("是否搬迁安置（2：已搬迁安置）")
	@Column(name = "is_satisfy")
	private String isSatisfy;

	@ApiModelProperty("复核人")
	@Column(name = "fh_nm")
	private String fhNm;

	@ApiModelProperty("公示状态")
	@Column(name = "gs_state")
	private String gsState;
	
	@ApiModelProperty("安置状态（0：正常，可去向界定；1：已修改，可导出界定表和发起流程；2：界定确认流程处理中，不可做任何操作；）")
	@Column(name = "move_state")
	private String moveState;
	
	@ApiModelProperty("分户状态（0：正常，可分户；1：分户申请流程处理中；2：分户申请流程通过；3：分户流程处理中；）")
	@Column(name = "split_household_state")
	private String splitHouseholdState;

	@ApiModelProperty("数据状态")
	@Column(name = "status")
	private String status;

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
	@Column(name = "modify_time", insertable = false)
	private Date modifyTime;
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user", insertable = false)
	private String modifyUser;

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;
	
	@ApiModelProperty(value = "生产安置状态（2:已生产安置）")
	@Column(name = "is_produce")
	private String isProduce;

	@ApiModelProperty(value = "生产安置去向")
	@Column(name = "produce_place")
	private String producePlace;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}