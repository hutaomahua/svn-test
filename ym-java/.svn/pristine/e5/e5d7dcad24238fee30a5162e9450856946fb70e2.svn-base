package com.lyht.business.abm.household.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分户与指标拆分表")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@GenericGenerator(name = "jpa-guid", strategy = "guid")
@Table(name = "t_abm_split_household")
public class AbmSplitHouseholdEntity {

	@Id
	@GeneratedValue(generator = "jpa-guid")
	@ApiModelProperty("ID")
	@Column(name = "id")
	private String id;

	@ApiModelProperty("户主nm")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("分户-流程ID")
	@Column(name = "apply_process_id")
	private String applyProcessId;

	@ApiModelProperty("分户申请-签字表附件")
	@Column(name = "sign_file_url")
	private String signFileUrl;

	@ApiModelProperty("分户-流程ID")
	@Column(name = "process_id")
	private String processId;

	@ApiModelProperty("分户-JSON数据")
	@Column(name = "split_json_data")
	private String splitJsonData;

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

}
