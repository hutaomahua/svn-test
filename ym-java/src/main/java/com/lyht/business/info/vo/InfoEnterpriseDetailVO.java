package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.Date;

public interface InfoEnterpriseDetailVO{
	
	Integer getId();
	String getNm();
	String getName();
	String getRegisterNumber();
	String getOperCond();
	BigDecimal getRegisterMoney();
	Date getRegisterTime();
	String getRegion();
	String getScope();
	String getStage();
	String getRegionValue();
	String getScopeValue();
	String getStageValue();
	
}