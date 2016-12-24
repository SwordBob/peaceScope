package com.p2p.model.sys.param;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import core.extjs.DateTimeSerializer;
import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class InvestmentParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 7656443663108619135L;
//	private String $like_investmentName;
//	private String $like_auditState;
//	private String $like_auditor;
	private Integer $eq_borrowId;
	private Integer $eq_userId;
	private String $like_phone;
	private String $like_title;
	private Integer $eq_investmentId;
	private String phone;// 用户电话
	private String email;// 用户邮箱
	// 应收本金
	private Float principal;
	// 应收利息
	private Float exceptInterest;
	// 实收本金
	private Float paidIn;
	// 实收利息
	private Float incomeInterest;

	private String title;
	// 借款金额
	private Integer borrowAmount;
	// 年利率
	private Float interest;
	// 项目期限
	private Integer stages;
	// 标种
	private String bidType;
	// 还款方式
	private String type;
	// 借款日期
	private Date borrowDate;
	// 借款状态
	private String state;
	// 借款人姓名
	private String bidName;
	// 借款人性别
	private String gender;
	// 借款人婚姻
	private String marriage;
	// 借款人籍贯
	private String address;

	/*public String get$like_investmentName() {
		return $like_investmentName;
	}

	public void set$like_investmentName(String $like_investmentName) {
		this.$like_investmentName = $like_investmentName;
	}

	public String get$like_auditState() {
		return $like_auditState;
	}

	public void set$like_auditState(String $like_auditState) {
		this.$like_auditState = $like_auditState;
	}

	public String get$like_auditor() {
		return $like_auditor;
	}

	public void set$like_auditor(String $like_auditor) {
		this.$like_auditor = $like_auditor;
	}

	public Integer get$eq_investmentId() {
		return $eq_investmentId;
	}

	public void set$eq_investmentId(Integer $eq_investmentId) {
		this.$eq_investmentId = $eq_investmentId;
	}*/

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getInterest() {
		return interest;
	}

	public void setInterest(Float interest) {
		this.interest = interest;
	}

	public Integer getStages() {
		return stages;
	}

	public void setStages(Integer stages) {
		this.stages = stages;
	}

	public String getBidType() {
		return bidType;
	}

	public void setBidType(String bidType) {
		this.bidType = bidType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBidName() {
		return bidName;
	}

	public void setBidName(String bidName) {
		this.bidName = bidName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Integer borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Float getIncomeInterest() {
		return incomeInterest;
	}

	public void setIncomeInterest(Float incomeInterest) {
		this.incomeInterest = incomeInterest;
	}

	@Override
	public String toString() {
		return "InvestmentParameter [phone=" + phone + ", email=" + email + ", principal=" + principal + ", exceptInterest=" + exceptInterest
				+ ", paidIn=" + paidIn + ", incomeInterest=" + incomeInterest + ", title=" + title + ", borrowAmount=" + borrowAmount + ", interest="
				+ interest + ", stages=" + stages + ", bidType=" + bidType + ", type=" + type + ", borrowDate=" + borrowDate + ", state=" + state
				+ ", bidName=" + bidName + ", gender=" + gender + ", marriage=" + marriage + ", address=" + address + "]";
	}

	public String get$like_phone() {
		return $like_phone;
	}

	public void set$like_phone(String $like_phone) {
		this.$like_phone = $like_phone;
	}

	public Integer get$eq_userId() {
		return $eq_userId;
	}

	public void set$eq_userId(Integer $eq_userId) {
		this.$eq_userId = $eq_userId;
	}

	public Integer get$eq_investmentId() {
		return $eq_investmentId;
	}

	public void set$eq_investmentId(Integer $eq_investmentId) {
		this.$eq_investmentId = $eq_investmentId;
	}

	public String get$like_title() {
		return $like_title;
	}

	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}

	public Integer get$eq_borrowId() {
		return $eq_borrowId;
	}

	public void set$eq_borrowId(Integer $eq_borrowId) {
		this.$eq_borrowId = $eq_borrowId;
	}

}
