package com.p2p.model.sys;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.AccountParameter;

//账户类
@Entity
@Table(name = "p2p_account")

@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = {"rechargeInfo","withdrawInfo","accountRecord","userInfo" })
public class Account extends AccountParameter {
	
	private static final long serialVersionUID = 1L;
	//账户ID
	@Id
	@GeneratedValue
	@Column(name = "accountId")
	private Integer accountId;
	//账户支付密码
	@Column(name = "payPassword", nullable = false)
	private String payPassword;
	//银行卡
	@Column(name = "bankCard", nullable = true)
	private String bankCard;
	//账户可用余额
	@Column(name = "balance", nullable = true)
	private Float balance;
	//账户冻结金额
	@Column(name = "frozen", nullable = true)
	private Float frozen;
	
	//充值信息类
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="accountid")
	private Set<RechargeInfo> rechargeInfo;
	//提现信息类
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="accountid")
	private Set<WithdrawInfo> withdrawInfo;
	//流水信息
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="accountid")
	private Set<AccountRecord> accountRecord;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)	
	private UserInfo userInfo;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Float getFrozen() {
		return frozen;
	}
	public void setFrozen(Float frozen) {
		this.frozen = frozen;
	}
	
	
	public Set<RechargeInfo> getRechargeInfo() {
		return rechargeInfo;
	}
	public void setRechargeInfo(Set<RechargeInfo> rechargeInfo) {
		this.rechargeInfo = rechargeInfo;
	}
	public Set<WithdrawInfo> getWithdrawInfo() {
		return withdrawInfo;
	}
	public void setWithdrawInfo(Set<WithdrawInfo> withdrawInfo) {
		this.withdrawInfo = withdrawInfo;
	}
	
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public Set<AccountRecord> getAccountRecord() {
		return accountRecord;
	}
	public void setAccountRecord(Set<AccountRecord> accountRecord) {
		this.accountRecord = accountRecord;
	}
	
	
	
	
	
}
