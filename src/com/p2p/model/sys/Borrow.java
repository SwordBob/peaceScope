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

import com.p2p.model.sys.param.BorrowParameter;

import core.extjs.DateTimeSerializer;

//借款类
@Entity
@Table(name = "p2p_borrow")

@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = { "otherInfo","investment","receivePlan"})
public class Borrow extends BorrowParameter {
	
	private static final long serialVersionUID = 1L;
	//借款ID
	@Id
	@GeneratedValue
	@Column(name = "borrowId")
	private Integer borrowId;
	//借款标题
	@Column(name = "title")
	private String title;
	//借款金额
	@Column(name = "amount")
	private Integer amount;
	//年利率
	@Column(name = "interest")
	private Float interest;
	//项目期限
	@Column(name = "stages")
	private Integer stages;
	//标种
	@Column(name = "bidType")
	private String bidType; 
	//还款方式
	@Column(name = "type")
	private String type;
	//借款日期
	@Column(name = "borrowDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date borrowDate;
	//借款状态
	@Column(name = "state")
	private String state;
	//借款人姓名
	@Column(name = "bidName")
	private String bidName;
	//借款人性别
	@Column(name = "gender")
	private String gender;
	//借款人婚姻
	@Column(name = "marriage")
	private String marriage;
	//最低借款
	@Column(name = "limitBorrowings")
	private Float limitBorrowings;
	//借款人籍贯
	@Column(name = "address")
	private String address;
	//抵押车辆信息
	@OneToOne
	@JoinColumn(name = "carInfoid")
	private CarInfo carInfo;
	@Column(name = "carInfoid", insertable = false, updatable = false)
	private Integer carInfoId; // 拼凑SQL，不会持久化到数据库,可以不用这个
	
	//借款用户信息
	@ManyToOne
	@JoinColumn(name = "userid")
	private UserInfo userInfo;
	@Column(name = "userid", insertable = false, updatable = false)
	private Integer userId; // 拼凑SQL，不会持久化到数据库
	//还款状态
	@OneToOne
	@JoinColumn(name = "repaymentstateid")
	private RepaymentState repaymentState;
	@Column(name = "repaymentstateid", insertable = false, updatable = false)
	private Integer repayId; // 拼凑SQL，不会持久化到数据库
	//其他资料
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="borrow")
	//@JoinColumn(name="borrowid")
	private Set<OtherInfo> otherInfo;
	//投资状况
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="borrow")
	//@JoinColumn(name="borrowid")
	private Set<Investment> investment;
	//还款计划
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="borrow")
	//@JoinColumn(name="borrowid")
	private Set<ReceivePlan> receivePlan;
	
	
	
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public Set<Investment> getInvestment() {
		return investment;
	}
	public void setInvestment(Set<Investment> investment) {
		this.investment = investment;
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
	public CarInfo getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}
	public Set<OtherInfo> getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(Set<OtherInfo> otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getBidType() {
		return bidType;
	}
	public void setBidType(String bidType) {
		this.bidType = bidType;
	}
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getCarInfoId() {
		return carInfoId;
	}
	public void setCarInfoId(Integer carInfoId) {
		this.carInfoId = carInfoId;
	}
	public RepaymentState getRepaymentState() {
		return repaymentState;
	}
	public void setRepaymentState(RepaymentState repaymentState) {
		this.repaymentState = repaymentState;
	}
	public Integer getRepayId() {
		return repayId;
	}
	public void setRepayId(Integer repayId) {
		this.repayId = repayId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Set<ReceivePlan> getReceivePlan() {
		return receivePlan;
	}
	public void setReceivePlan(Set<ReceivePlan> receivePlan) {
		this.receivePlan = receivePlan;
	}
	public Float getLimitBorrowings() {
		return limitBorrowings;
	}
	public void setLimitBorrowings(Float limitBorrowings) {
		this.limitBorrowings = limitBorrowings;
	}
	
	
	
}
