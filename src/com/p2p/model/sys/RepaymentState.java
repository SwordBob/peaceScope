package com.p2p.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.RepaymentStateParameter;

import core.extjs.DateTimeSerializer;

//还款明细
@Entity
@Table(name = "p2p_RepaymentState")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = { "borrow"})
public class RepaymentState extends RepaymentStateParameter {
	
	private static final long serialVersionUID = 1L;
	//还款明细ID
	@Id
	@GeneratedValue
	@Column(name = "repaymentStateId")
	private Integer repaymentStateId;
	//还款日期
	@Column(name = "repaymentDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date repaymentDate;
	//已还期数
	@Column(name = "payet")
	private Integer payet;
	//还款截止日期
	@Column(name = "repaymentEnd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date repaymentEnd;
	//应还金额
	@Column(name = "ESTIMATE")
	private Float estimate;
	//已还金额
	@Column(name = "actual")
	private Float actual;
	//逾期罚款
	@Column(name = "penalty")
	private Float penalty;
	//借款类
	@OneToOne(mappedBy="repaymentState")
	private Borrow borrow;
	
		
	public Integer getRepaymentStateId() {
		return repaymentStateId;
	}
	public void setRepaymentStateId(Integer repaymentStateId) {
		this.repaymentStateId = repaymentStateId;
	}
	
	public Float getEstimate() {
		return estimate;
	}
	public void setEstimate(Float estimate) {
		this.estimate = estimate;
	}
	public Float getActual() {
		return actual;
	}
	public void setActual(Float actual) {
		this.actual = actual;
	}
	
	public Float getPenalty() {
		return penalty;
	}
	public void setPenalty(Float penalty) {
		this.penalty = penalty;
	}
	
	public Integer getPayet() {
		return payet;
	}
	public void setPayet(Integer payet) {
		this.payet = payet;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public Borrow getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getRepaymentEnd() {
		return repaymentEnd;
	}
	public void setRepaymentEnd(Date repaymentEnd) {
		this.repaymentEnd = repaymentEnd;
	}
	
	
	
	
	
	
}
