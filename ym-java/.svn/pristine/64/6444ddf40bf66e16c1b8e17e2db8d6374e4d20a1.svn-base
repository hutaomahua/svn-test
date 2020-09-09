package com.lyht.business.abm.removal.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("安置人口界定及去向确认确认流程表")
@Data
@Entity
@Table(name = "t_abm_move_process")
@EntityListeners(AuditingEntityListener.class)
@GenericGenerator(name = "jpa-guid", strategy = "guid")
public class AbmMoveProcessEntity {

	@ApiModelProperty("主键")
	@Id
	@GeneratedValue(generator = "jpa-guid")
	@Column(name = "id")
	private String id;

	@ApiModelProperty("户主NM")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("流程ID")
	@Column(name = "process_id")
	private String processId;

	@ApiModelProperty("签字表附件")
	@Column(name = "sign_file_url")
	private String signFileUrl;

	@ApiModelProperty("安置数据")
	@Column(name = "move_json_data")
	private String moveJsonData;

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;

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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
