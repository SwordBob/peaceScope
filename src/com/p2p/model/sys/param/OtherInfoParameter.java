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
public class OtherInfoParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 7656443663108619135L;
	private String $like_otherInfoName;
	private String $like_auditState;
	private String $like_auditor;
	private Integer $eq_otherInfoId;
	private Integer $eq_borrowId;
	//private String imgFilePath;
	private Integer borrowId;
	
	private String title;
	//借款金额
	private Integer amount;
	//年利率
	private Float interest;
	//项目期限	
	private Integer stages;
	//标种	
	private String bidType; 
	//还款方式	
	private String type;
	//借款日期
	private Date borrowDate;
	//借款状态
	private String state;
	//借款人姓名
	private String bidName;
	//借款人性别
	private String gender;
	//借款人婚姻
	private String marriage;
	//借款人籍贯
	private String address;
	
	public String get$like_otherInfoName() {
		return $like_otherInfoName;
	}
	public void set$like_otherInfoName(String $like_otherInfoName) {
		this.$like_otherInfoName = $like_otherInfoName;
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
	public Integer get$eq_otherInfoId() {
		return $eq_otherInfoId;
	}
	public void set$eq_otherInfoId(Integer $eq_otherInfoId) {
		this.$eq_otherInfoId = $eq_otherInfoId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public Integer get$eq_borrowId() {
		return $eq_borrowId;
	}
	public void set$eq_borrowId(Integer $eq_borrowId) {
		this.$eq_borrowId = $eq_borrowId;
	}
	
	
	

	

	

}
