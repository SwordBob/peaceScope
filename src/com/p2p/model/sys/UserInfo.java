package com.p2p.model.sys;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.UserInfoParameter;

import core.extjs.DateTimeSerializer;

//用户信息类
@Entity
@Table(name = "p2p_userinfo")

@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = {"borrow","investment","loginInfo","myMessages" })
public class UserInfo extends UserInfoParameter {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "userid")
	private Integer userId;//用户ID
	@Column(name = "phone", nullable = true)
	private String phone;//用户电话
	@Column(name = "password", nullable = false)
	private String password;//用户登录密码
	@Column(name = "email", nullable = true)
	private String email;//用户邮箱
	@Column(name = "createdate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;//注册日期
	@OneToOne
	@JoinColumn(name = "accountid")
	private Account account;//用户账户类
	@Column(name = "accountid", insertable = false, updatable = false)
	private Integer accountId; // 拼凑SQL，不会持久化到数据库
	@OneToOne
	@JoinColumn(name = "authzid")	
	private Authz authz;//用户认证类
	@Column(name = "authzid", insertable = false, updatable = false)
	private Integer authzId; // 拼凑SQL，不会持久化到数据库
	@OneToOne
	@JoinColumn(name = "contactid")	
	private Contacts contacts;//用户紧急联系人
	@Column(name = "contactid", insertable = false, updatable = false)
	private Integer contactId; // 拼凑SQL，不会持久化到数据库
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="userid")
	private Set<Borrow> borrow;//借款类
	@OneToMany(cascade = CascadeType.ALL,mappedBy="userInfo", fetch = FetchType.LAZY)
	//@JoinColumn(name="userid")
	private Set<Investment> investment;//投资类
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="userid")
	private Set<LoginInfo> loginInfo;//登录时间类
	@OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
	@JoinColumn(name="userid")
	private Set<MyMessage> myMessages;//信息类
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Authz getAuthz() {
		return authz;
	}
	public void setAuthz(Authz authz) {
		this.authz = authz;
	}
	public Contacts getContacts() {
		return contacts;
	}
	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}
	public Set<Borrow> getBorrow() {
		return borrow;
	}
	public void setBorrow(Set<Borrow> borrow) {
		this.borrow = borrow;
	}
	public Set<Investment> getInvestment() {
		return investment;
	}
	public void setInvestment(Set<Investment> investment) {
		this.investment = investment;
	}
	
	public Set<LoginInfo> getLoginInfo() {
		return loginInfo;
	}
	public void setLoginInfo(Set<LoginInfo> loginInfo) {
		this.loginInfo = loginInfo;
	}
	public Set<MyMessage> getMyMessages() {
		return myMessages;
	}
	public void setMyMessages(Set<MyMessage> myMessages) {
		this.myMessages = myMessages;
	}
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getAuthzId() {
		return authzId;
	}
	public void setAuthzId(Integer authzId) {
		this.authzId = authzId;
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
