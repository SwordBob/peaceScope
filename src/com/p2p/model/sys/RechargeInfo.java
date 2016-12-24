package com.p2p.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import core.extjs.DateTimeSerializer;

//充值类
@Entity
@Table(name = "p2p_rechargeinfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RechargeInfo {

	//充值ID
	@Id
	@GeneratedValue
	@Column(name = "rechargeId")
	private Integer rechargeId;
	//充值金额
	@Column(name = "amount", nullable = true)
	private Float amount;
	//充值银行
	@Column(name = "rechargeBank", nullable = true)
	private String rechargeBank;
	//充值日期
	@Column(name = "i_date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public Integer getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(Integer rechargeId) {
		this.rechargeId = rechargeId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRechargeBank() {
		return rechargeBank;
	}
	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}
	
	
	
}
