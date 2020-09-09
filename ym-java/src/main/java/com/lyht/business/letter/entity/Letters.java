package com.lyht.business.letter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangTianhao
 * @date 2019年10月30日17:08:14
 */

@Entity
@Table(name = "t_abm_letters")
@ApiModel(description = "信访")
public class Letters implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	/**
	 * 内码
	 */
	@Column(name = "nm")
	private String nm;
	/**
	 * 信访编号
	 */
	@Column(name = "code")
	private String code;
	/**
	 * 流水号
	 */
	@Column(name = "water")
	private String water;
	/**
	 * 信访形式
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 信访类别
	 */
	@Column(name = "category")
	private String category;
	/**
	 * 信访人
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 联系电话
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 联系电话
	 */
	@Column(name = "sex")
	private Integer sex;
	/**
	 * 信访时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "visits_time")
	private Date visitsTime;
	@Transient
	private String visitsTimes;
	/**
	 * 主题
	 */
	@Column(name = "theme")
	private String theme;
	/**
	 * 接待时间
	 */
	@Column(name = "reception_time")
	private Date receptionTime;
	/**
	 * 办结时间
	 */
	@Column(name = "through_time")
	private Date throughTime;
	/**
	 * 信访人地址
	 */
	@Column(name = "visits_addr")
	private String visitsAddr;
	/**
	 * 信访内容
	 */
	@Column(name = "visits_content")
	private String visitsContent;
	/**
	 * 拟办意见
	 */
	@Column(name = "visits_opinion")
	private String visitsOpinion;
	/**
	 * 领导意见
	 */
	@Column(name = "leader_opinions")
	private String leaderOpinions;
	/**
	 * 承办人
	 */
	@Column(name = "undertake")
	private String undertake;
	/**
	 * 承办单位
	 */
	@Column(name = "undertake_unit")
	private String undertakeUnit;
	/**
	 * 承办人电话
	 */
	@Column(name = "undertake_phone")
	private String undertakePhone;
	/**
	 * 办结人
	 */
	@Column(name = "through_name")
	private String throughName;
	/**
	 * 办结内容
	 */
	@Column(name = "through_content")
	private String throughContent;
	/**
	 * 行政区
	 * @return
	 */
	private String region;
	
	/**
	 * 职业
	 * @return
	 */
	private String job;
	/**
	 * 所在单位或地址
	 * @return
	 */
	private String dwdz;
	/**
	 * 信访事项
	 * @return
	 */
	private String matter;
	
	public String getDwdz() {
		return dwdz;
	}
	public String getMatter() {
		return matter;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}

	public String getVisitsTimes() {
		return visitsTimes;
	}

	public void setVisitsTimes(String visitsTimes) {
		this.visitsTimes = visitsTimes;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getId() {
		return id;
	}
	public String getNm() {
		return nm;
	}
	public String getCode() {
		return code;
	}
	public String getWater() {
		return water;
	}
	public String getType() {
		return type;
	}
	public String getCategory() {
		return category;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public Date getVisitsTime() {
		return visitsTime;
	}
	public String getTheme() {
		return theme;
	}
	public Date getReceptionTime() {
		return receptionTime;
	}
	public Date getThroughTime() {
		return throughTime;
	}
	public String getVisitsAddr() {
		return visitsAddr;
	}
	public String getVisitsContent() {
		return visitsContent;
	}
	public String getVisitsOpinion() {
		return visitsOpinion;
	}
	public String getLeaderOpinions() {
		return leaderOpinions;
	}
	public String getUndertake() {
		return undertake;
	}
	public String getUndertakeUnit() {
		return undertakeUnit;
	}
	public String getUndertakePhone() {
		return undertakePhone;
	}
	public String getThroughName() {
		return throughName;
	}
	public String getThroughContent() {
		return throughContent;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setVisitsTime(Date visitsTime) {
		this.visitsTime = visitsTime;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public void setReceptionTime(Date receptionTime) {
		this.receptionTime = receptionTime;
	}
	public void setThroughTime(Date throughTime) {
		this.throughTime = throughTime;
	}
	public void setVisitsAddr(String visitsAddr) {
		this.visitsAddr = visitsAddr;
	}
	public void setVisitsContent(String visitsContent) {
		this.visitsContent = visitsContent;
	}
	public void setVisitsOpinion(String visitsOpinion) {
		this.visitsOpinion = visitsOpinion;
	}
	public void setLeaderOpinions(String leaderOpinions) {
		this.leaderOpinions = leaderOpinions;
	}
	public void setUndertake(String undertake) {
		this.undertake = undertake;
	}
	public void setUndertakeUnit(String undertakeUnit) {
		this.undertakeUnit = undertakeUnit;
	}
	public void setUndertakePhone(String undertakePhone) {
		this.undertakePhone = undertakePhone;
	}	
	public void setThroughName(String throughName) {
		this.throughName = throughName;
	}	
	public void setThroughContent(String throughContent) {
		this.throughContent = throughContent;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
