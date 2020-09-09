package com.lyht.business.info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

/**
 * 建设征地实物指标汇总表
 * 
 * @author wzw
 *
 */
@Entity
@Table(name = "t_info_statistics")
public class InfoStatistics {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 项目
	private String A;
	// 单位
	private String B;
	// 总计
	private String C;
	// 枢纽建设 合计
	private String D;
	// 临时占地
	private String E;
	// 永久占地
	private String F;
	// 水库淹没影响合计
	private String G;
	// 水库淹没
	private String H;
	// 水库影响
	private String I;
	// 垫防临时
	private String J;
	// 集镇新址
	private String K;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getA() {
		return A;
	}

	public String getB() {
		return B;
	}

	public String getC() {
		return C;
	}

	public String getD() {
		return D;
	}

	public String getE() {
		return E;
	}

	public String getF() {
		return F;
	}

	public String getG() {
		return G;
	}

	public String getH() {
		return H;
	}

	public String getI() {
		return I;
	}

	public String getJ() {
		return J;
	}

	public String getK() {
		return K;
	}

	public void setA(String a) {
		A = a;
	}

	public void setB(String b) {
		B = b;
	}

	public void setC(String c) {
		C = c;
	}

	public void setD(String d) {
		D = d;
	}

	public void setE(String e) {
		E = e;
	}

	public void setF(String f) {
		F = f;
	}

	public void setG(String g) {
		G = g;
	}

	public void setH(String h) {
		H = h;
	}

	public void setI(String i) {
		I = i;
	}

	public void setJ(String j) {
		J = j;
	}

	public void setK(String k) {
		K = k;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
