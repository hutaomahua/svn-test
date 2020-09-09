package com.lyht.business.abm.plan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "土地公示户主列表")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_td_publicity_details")
public class TdPublicityDetailEntity {
	 private static final long serialVersionUID = 1L;

	    /**
	     * 主键id
	     */
	    @ApiModelProperty(value = "唯一ID，修改必填")
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;

	   
	    @ApiModelProperty(value = "唯一内码 ")
	    private String nm;
	  

	    @ApiModelProperty(value = "户主nm ")
	    @Column(name = "owner_nm", nullable = false)
	    private String ownerNm;
	    
	    @ApiModelProperty(value = "指标类型 ")
	    @Column(name = "zb_type", nullable = false)
	    private String zbType;
	    
	    @ApiModelProperty(value = "公式结束时间 ")
	    @Column(name = "end_time", nullable = false)
	    private String endTime;
	    
	    @ApiModelProperty(value = "流程id")
	    @Column(name = "process_id", nullable = false)
	    private String processId;
	    
	    
	    

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



		public String getOwnerNm() {
			return ownerNm;
		}



		public void setOwnerNm(String ownerNm) {
			this.ownerNm = ownerNm;
		}



		public String getZbType() {
			return zbType;
		}



		public void setZbType(String zbType) {
			this.zbType = zbType;
		}



		public String getEndTime() {
			return endTime;
		}



		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}



		@Override
	    public String toString() {
	        return JSON.toJSONString(this);
	    }



		public String getProcessId() {
			return processId;
		}



		public void setProcessId(String processId) {
			this.processId = processId;
		}
		
		

}
