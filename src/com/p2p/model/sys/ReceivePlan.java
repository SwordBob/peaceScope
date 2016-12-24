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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.ReceivePlanParameter;


import core.extjs.DateTimeSerializer;

//还款计划
@Entity
@Table(name = "p2p_ReceivePlan")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = { "borrow"})
public class ReceivePlan extends ReceivePlanParameter {
	
	private static final long serialVersionUID = 1L;
	//还款计划ID
	@Id
	@GeneratedValue
	@Column(name = "receivePlanId")
	private Integer receivePlanId;
	//还款日期
	@Column(name = "planDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date planDate;	
	//还款截止日期
	@Column(name = "planDateEnd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date planDateEnd;
	//应还金额
	@Column(name = "planEstimate")
	private Float planEstimate;
	//所属期数
	@Column(name = "partStages")
	private Integer partStages;
	//逾期罚款
	@Column(name = "planPenalty")
	private Float planPenalty;
	//借款类
	@ManyToOne
	@JoinColumn(name="borrowid")
	private Borrow borrow;
	@Column(name = "borrowid", insertable = false, updatable = false)
	private Integer borrowId; // 拼凑SQL，不会持久化到数据库,可以不用这个
	//还款状态
	private String receiveState;
	
		
	public Integer getReceivePlanId() {
		return receivePlanId;
	}
	public void setReceivePlanId(Integer receivePlanId) {
		this.receivePlanId = receivePlanId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	public Float getPlanEstimate() {
		return planEstimate;
	}
	public void setPlanEstimate(Float planEstimate) {
		this.planEstimate = planEstimate;
	}
	
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getPlanDateEnd() {
		return planDateEnd;
	}
	public void setPlanDateEnd(Date planDateEnd) {
		this.planDateEnd = planDateEnd;
	}
	public String getReceiveState() {
		return receiveState;
	}
	public void setReceiveState(String receiveState) {
		this.receiveState = receiveState;
	}
	
	public Float getPlanPenalty() {
		return planPenalty;
	}
	public void setPlanPenalty(Float planPenalty) {
		this.planPenalty = planPenalty;
	}
	public Integer getPartStages() {
		return partStages;
	}
	public void setPartStages(Integer partStages) {
		this.partStages = partStages;
	}
	
	
	
	
	
	
	
}
