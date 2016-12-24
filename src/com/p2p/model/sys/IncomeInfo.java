package com.p2p.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

//收益明细
@Entity
@Table(name = "p2p_incomeInfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IncomeInfo {
	
	//明细ID
	@Id
	@GeneratedValue
	@Column(name = "incomeInfoId")
	private Integer incomeInfoId;
	//应收本金
	@Column(name = "principal")
	private Float principal;
	//应收利息
	@Column(name = "exceptInterest")
	private Float exceptInterest;
	//实收本金
	@Column(name = "paidIn")
	private Float paidIn;
	//实收利息
	@Column(name = "interest")
	private Float interest;
	public Integer getIncomeInfoId() {
		return incomeInfoId;
	}
	public void setIncomeInfoId(Integer incomeInfoId) {
		this.incomeInfoId = incomeInfoId;
	}
	public Float getPrincipal() {
		return principal;
	}
	public void setPrincipal(Float principal) {
		this.principal = principal;
	}
	public Float getExceptInterest() {
		return exceptInterest;
	}
	public void setExceptInterest(Float exceptInterest) {
		this.exceptInterest = exceptInterest;
	}
	public Float getPaidIn() {
		return paidIn;
	}
	public void setPaidIn(Float paidIn) {
		this.paidIn = paidIn;
	}
	public Float getInterest() {
		return interest;
	}
	public void setInterest(Float interest) {
		this.interest = interest;
	}
	
	
	
}
