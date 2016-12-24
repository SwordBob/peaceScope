package com.p2p.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.InvestmentParameter;

import core.extjs.DateTimeSerializer;

//投资类
@Entity
@Table(name = "p2p_investment")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Investment extends InvestmentParameter{
	
	//投资ID
	@Id
	@GeneratedValue
	@Column(name = "investmentId")
	private Integer investmentId;
	//投资金额
	@Column(name = "amount")
	private Float amount;
	//预计收益
	@Column(name = "expect")
	private Float expect;
	//投标时间
	@Column(name = "investDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date investDate;
	//借款类
	@ManyToOne
	@JoinColumn(name = "borrowid", nullable = false)
	private Borrow borrow;
	@Column(name = "borrowid", insertable = false, updatable = false)
	private Integer borrowId; // 拼凑SQL，不会持久化到数据库
	//收益明细
	@ManyToOne
	@JoinColumn(name = "incomeInfoId", nullable = false)
	private IncomeInfo incomeInfo;
	@Column(name = "incomeInfoId", insertable = false, updatable = false)
	private Integer incomeInfoId; // 拼凑SQL，不会持久化到数据库
	//用户信息
	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
	private UserInfo userInfo;
	@Column(name = "userid", insertable = false, updatable = false)
	private Integer userId; // 拼凑SQL，不会持久化到数据库
	
	public Integer getInvestmentId() {
		return investmentId;
	}
	public void setInvestmentId(Integer investmentId) {
		this.investmentId = investmentId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getInvestDate() {
		return investDate;
	}
	public void setInvestDate(Date investDate) {
		this.investDate = investDate;
	}
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	public IncomeInfo getIncomeInfo() {
		return incomeInfo;
	}
	public void setIncomeInfo(IncomeInfo incomeInfo) {
		this.incomeInfo = incomeInfo;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public Float getExpect() {
		return expect;
	}
	public void setExpect(Float expect) {
		this.expect = expect;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public Integer getIncomeInfoId() {
		return incomeInfoId;
	}
	public void setIncomeInfoId(Integer incomeInfoId) {
		this.incomeInfoId = incomeInfoId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
    
}
