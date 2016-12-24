package com.p2p.model.sys.param;

import java.util.Date;

import javax.persistence.Column;

import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class UserInfoParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 7656443663108619135L;
	private Integer $eq_userId;
	private String $like_phone;
	private String $like_email;
	private Integer accountId;
	private String $like_createDate;
	private Integer $eq_accountId;
	// 银行卡
	// private String bankCard;
	// 账户可用余额
	private Float balance;
	// 账户冻结金额
	private Float frozen;
	// 紧急联系人姓名
	private String contactName;
	// 紧急联系人电话
	private String contactPhone;

	private Integer contactId;

	// 用户真实姓名

	private String realName;
	// 用户身份证号

	private String idCardNO;

	public String get$like_phone() {
		return $like_phone;
	}

	public void set$like_phone(String $like_phone) {
		this.$like_phone = $like_phone;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String get$like_email() {
		return $like_email;
	}

	public void set$like_email(String $like_email) {
		this.$like_email = $like_email;
	}

	public Integer get$eq_accountId() {
		return $eq_accountId;
	}

	public void set$eq_accountId(Integer $eq_accountId) {
		this.$eq_accountId = $eq_accountId;
	}

	public Integer get$eq_userId() {
		return $eq_userId;
	}

	public void set$eq_userId(Integer $eq_userId) {
		this.$eq_userId = $eq_userId;
	}

	public String get$like_createDate() {
		return $like_createDate;
	}

	public void set$like_createDate(String $like_createDate) {
		this.$like_createDate = $like_createDate;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

}
