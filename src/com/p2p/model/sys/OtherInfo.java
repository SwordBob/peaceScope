package com.p2p.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.OtherInfoParameter;

//其他资料类
@Entity
@Table(name = "p2p_OtherInfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OtherInfo extends OtherInfoParameter{
	//其他资料类ID
	@Id
	@GeneratedValue
	@Column(name = "otherInfoId")
	private Integer otherInfoId;
	//其他资料名称
	@Column(name = "otherInfoName")
	private String otherInfoName;
	//其他资料审核状态
	@Column(name = "auditState")
	private String auditState;
	//其他资料审核人
	@Column(name = "auditor")
	private String auditor;
	//路径
	@Column(name = "otherInfoUrl")
	private String otherInfoUrl;	
		
	//借款类
	@ManyToOne
	@JoinColumn(name = "borrowid", nullable = false)
	private Borrow borrow;	
	@Column(name = "borrowid", insertable = false, updatable = false)
	private Integer borrowId; // 拼凑SQL，不会持久化到数据库
	
	
	
	public Integer getOtherInfoId() {
		return otherInfoId;
	}
	public void setOtherInfoId(Integer otherInfoId) {
		this.otherInfoId = otherInfoId;
	}
	public String getOtherInfoName() {
		return otherInfoName;
	}
	public void setOtherInfoName(String otherInfoName) {
		this.otherInfoName = otherInfoName;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
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
	public String getOtherInfoUrl() {
		return otherInfoUrl;
	}
	public void setOtherInfoUrl(String otherInfoUrl) {
		this.otherInfoUrl = otherInfoUrl;
	}
	
	
}
