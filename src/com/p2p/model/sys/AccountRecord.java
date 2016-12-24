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

import com.p2p.model.sys.param.AccountRecordParameter;

import core.extjs.DateTimeSerializer;
//账户流水
@Entity
@Table(name = "p2p_accountrecord")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountRecord extends AccountRecordParameter {
	//流水ID
	@Id
	@GeneratedValue
	@Column(name = "accountRecordId")
	private Integer accountRecordId;
	//交易账户类型
	@Column(name = "accountType", nullable = true)
	private String accountType;
	//交易类型
	@Column(name = "tradeProcesses", nullable = true)
	private String tradeProcesses;
	//交易金额
	@Column(name = "amount", nullable = true)
	private Float amount;
	//交易时间
	@Column(name = "tradeTime", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tradeTime;
	//交易目标
	@Column(name = "tradeTraget", nullable = true)
	private String tradeTraget;
	//账户id
	@Column(name = "accountId", insertable = false, updatable = false)
	private Integer accountId; // 拼凑SQL，不会持久化到数据库,可以不用这个
	
	public Integer getAccountRecordId() {
		return accountRecordId;
	}
	public void setAccountRecordId(Integer accountRecordId) {
		this.accountRecordId = accountRecordId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getTradeProcesses() {
		return tradeProcesses;
	}
	public void setTradeProcesses(String tradeProcesses) {
		this.tradeProcesses = tradeProcesses;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTradeTraget() {
		return tradeTraget;
	}
	public void setTradeTraget(String tradeTraget) {
		this.tradeTraget = tradeTraget;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	
}
