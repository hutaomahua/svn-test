package com.lyht.business.letter.vo;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class LettersVO {

	private String word;

	private Integer id;
	
	private String nm;
	
	private String name;
	
	private String phone;
	
	private Date visitsTime;
	
	private String dwdz;
	
	private String matter;
	
	private String visitsContent;
	
	private String sex;
	
	private String job;
	
	private String undertake;
	
	private String cityCode;
	
	private String mergerName;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
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

	public String getDwdz() {
		return dwdz;
	}

	public String getMatter() {
		return matter;
	}

	public String getVisitsContent() {
		return visitsContent;
	}

	public String getSex() {
		return sex;
	}

	public String getJob() {
		return job;
	}

	public String getUndertake() {
		return undertake;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getMergerName() {
		return mergerName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
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

	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}

	public void setVisitsContent(String visitsContent) {
		this.visitsContent = visitsContent;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setUndertake(String undertake) {
		this.undertake = undertake;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setMergerName(String mergerName) {
		this.mergerName = mergerName;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
