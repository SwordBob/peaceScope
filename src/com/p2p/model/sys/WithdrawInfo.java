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

//提现类
@Entity
@Table(name = "p2p_withdrawinfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WithdrawInfo {

	//提现ID
	@Id
	@GeneratedValue
	@Column(name = "withdrawId")
	private Integer withdrawId;
	//提现银行
	@Column(name = "bankName", nullable = true)
	private String bankName;
	//提现金额
	@Column(name = "amount", nullable = true)
	private Float amount;
	//手续费
	@Column(name = "fee", nullable = true)
	private Float fee;
	//提现银行账号
	@Column(name = "cardNO", nullable = true)
	private String cardNO;
	//提现日期
	@Column(name = "extractDate", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date extractDate;
	//提现状态
	@Column(name = "state", nullable = true)
	private String state;
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getWithdrawId() {
		return withdrawId;
	}
	public void setWithdrawId(Integer withdrawId) {
		this.withdrawId = withdrawId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getExtractDate() {
		return extractDate;
	}
	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}
	

}
