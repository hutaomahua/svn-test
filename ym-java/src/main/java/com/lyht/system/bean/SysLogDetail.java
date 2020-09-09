package com.lyht.system.bean;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "日志信息")
public class SysLogDetail {
	
		@ApiModelProperty(value="唯一ID")
	    private Integer id;
	   
		@ApiModelProperty(value="数据内码")
	    private String nm;
	  
		@ApiModelProperty(value="操作时间")
	    private String logtime;
	   
		@ApiModelProperty(value="操作员")
	    private String name;
	   
		@ApiModelProperty(value="操作员内码")
	    private String opernm;
	   
		@ApiModelProperty(value="模块唯一标识")
	    private String menuflag;
	  
		@ApiModelProperty(value="操作类型")
	    private String dictnmOpttype;
	  
		@ApiModelProperty(value="旧数据")
	    private String olddata;
	   
		@ApiModelProperty(value="新数据")
	    private String newdata;
	  
		@ApiModelProperty(value="备注")
	    private String memo;
	   
		@ApiModelProperty(value="操作状态")
	    private Integer flag;
	   
		@ApiModelProperty(value="系统标识")
	    private Integer sysflag;
	    
	    /** default constructor */
	    public SysLogDetail() {
	    
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
		public String getLogtime() {
			return logtime;
		}
		public void setLogtime(String logtime) {
			this.logtime = logtime;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOpernm() {
			return opernm;
		}
		public void setOpernm(String opernm) {
			this.opernm = opernm;
		}
		public String getMenuflag() {
			return menuflag;
		}
		public void setMenuflag(String menuflag) {
			this.menuflag = menuflag;
		}
		public String getDictnmOpttype() {
			return dictnmOpttype;
		}
		public void setDictnmOpttype(String dictnmOpttype) {
			this.dictnmOpttype = dictnmOpttype;
		}
		public String getOlddata() {
			return olddata;
		}
		public void setOlddata(String olddata) {
			this.olddata = olddata;
		}
		public String getNewdata() {
			return newdata;
		}
		public void setNewdata(String newdata) {
			this.newdata = newdata;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public Integer getFlag() {
			return flag;
		}
		public void setFlag(Integer flag) {
			this.flag = flag;
		}
		public Integer getSysflag() {
			return sysflag;
		}
		public void setSysflag(Integer sysflag) {
			this.sysflag = sysflag;
		}
	    
}
