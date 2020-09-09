package com.lyht.business.abm.paymentManagement.vo;

import io.swagger.annotations.ApiModelProperty;

public interface InfoListVO {

	@ApiModelProperty(value="id")
	String getId();
	
	@ApiModelProperty(value="记录内码")
	String getNm();
	
	@ApiModelProperty(value="权属人内码")
	String getOwnernm();
	
	@ApiModelProperty(value="权属人")
	String getOwner();
	
	@ApiModelProperty(value="安置类型内码")
	String getMovetypenm();
	
	@ApiModelProperty(value="安置类型")
	String getMovetype();
	
	@ApiModelProperty(value="协议名称")
	String getProtocolname();
	
	@ApiModelProperty(value="协议编码")
	String getProtocolcode();
	
	@ApiModelProperty(value="协议总金额")
	String getProtocolamount();
	
	@ApiModelProperty(value="已申请金额")
	String getProtocolpayed();
	
	@ApiModelProperty(value="待申请金额")
	String getProtocolsurplus();
	
	@ApiModelProperty(value="协议类型 0：补偿协议 1：资金代管协议")
	Integer getProtocoltype();
	
	@ApiModelProperty(value="申请批次")
	Integer getBatch();
	
	@ApiModelProperty(value="是否允许下一次兑付")
	Integer getIsnext();
	
	@ApiModelProperty(value="最后申请时间")
	String getApplytime();
	
	@ApiModelProperty(value="附件名称")
	String getFilename();
	
	@ApiModelProperty(value="附件URL")
	String getFileurl();
	
	@ApiModelProperty(value = "流程处理状态 {Approved:同意,Deleted:已取消,Rejected:拒绝,Standby:处理中}")
	String getStatus();
	
	@ApiModelProperty(value = "流程处理状态明文 {Approved:同意,Deleted:已取消,Rejected:拒绝,Standby:处理中}")
	String getCnstatus();
}
