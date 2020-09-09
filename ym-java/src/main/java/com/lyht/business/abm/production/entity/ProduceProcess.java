package com.lyht.business.abm.production.entity;

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

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("生产安置人口界定及去向确认确认流程表")
@Entity
@Table(name = "t_abm_produce_process")
@EntityListeners(AuditingEntityListener.class)
@GenericGenerator(name = "jpa-guid", strategy = "guid")
@Data
public class ProduceProcess {

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
	
	@ApiModelProperty(value = "生产安置数据")
	@Column(name = "json_data")
	private String jsonData;

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
	
	@ApiModelProperty(value = "备注")
	private String remark;
}
