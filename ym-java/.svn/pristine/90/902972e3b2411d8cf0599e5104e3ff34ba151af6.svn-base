package com.lyht.business.abm.production.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_abm_production_audit")
@ApiModel(description = "用户界定审核")
@Data
@ToString
public class ProductionAuditEntity {

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

	@ApiModelProperty(value = "流程ID")
	@Column(name = "process_id")
	private String processId;

	@ApiModelProperty(value = "审核申请时间")
	@Column(name = "apply_date")
	private Date applyDate;

	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "界定状态 0待界定 1不通过 2通过 3界定中")
	@Column(name = "is_produce")
	private String isProduce;

}
