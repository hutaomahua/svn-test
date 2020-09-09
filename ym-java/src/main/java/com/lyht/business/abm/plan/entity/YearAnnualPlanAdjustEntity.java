package com.lyht.business.abm.plan.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "年度计划编制表调整")
@Entity
@Table(name = "t_abm_year_plan_log")
public class YearAnnualPlanAdjustEntity implements Serializable {
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
             
        @ApiModelProperty(value = "数据唯一内码 ")
        @Column(name = "data_nm", nullable = false)
        private String dataNm;
             
        @ApiModelProperty(value = "行政去")
        private String region;
        
        @ApiModelProperty(value = "年份")
        private String year;
        
        @ApiModelProperty(value = "保存人")
        private String name;
        
        @ApiModelProperty(value = "保存数据")
        private String save_date;
        
        
        

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




		public String getDataNm() {
			return dataNm;
		}




		public void setDataNm(String dataNm) {
			this.dataNm = dataNm;
		}




		public String getRegion() {
			return region;
		}




		public void setRegion(String region) {
			this.region = region;
		}




		public String getYear() {
			return year;
		}




		public void setYear(String year) {
			this.year = year;
		}




		public String getName() {
			return name;
		}




		public void setName(String name) {
			this.name = name;
		}




		public String getSave_date() {
			return save_date;
		}




		public void setSave_date(String save_date) {
			this.save_date = save_date;
		}




		@Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }