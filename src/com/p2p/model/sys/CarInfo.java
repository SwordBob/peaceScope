package com.p2p.model.sys;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.p2p.model.sys.param.CarInfoParameter;

import core.extjs.DateTimeSerializer;

//抵押车辆信息
@Entity
@Table(name = "p2p_carinfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = { "borrow"})
public class CarInfo extends CarInfoParameter {
	//抵押车辆编号
	@Id
	@GeneratedValue
	@Column(name = "carInfoId")
	private Integer carInfoId;
	//车辆品牌
	@Column(name = "carBrand", nullable = true)
	private String carBrand;
	//车牌号
	@Column(name = "carId", nullable = true)
	private String carId;
	//公里数
	@Column(name = "mileage", nullable = true)
	private Integer mileage;
	//购买价格
	@Column(name = "orgPrice", nullable = true)
	private Float orgPrice;
	//抵押价格
	@Column(name = "evaluate", nullable = true)
	private Float evaluate;
	//审核时间
	@Column(name = "auditDate", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditDate;
	//审核人
	@Column(name = "auditor", nullable = true)
	private String auditor;
	//借款类
	@OneToOne(cascade = CascadeType.ALL, mappedBy="carInfo", fetch = FetchType.LAZY)
	private Borrow borrow;
	public Integer getCarInfoId() {
		return carInfoId;
	}
	public void setCarInfoId(Integer carInfoId) {
		this.carInfoId = carInfoId;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	public Float getOrgPrice() {
		return orgPrice;
	}
	public void setOrgPrice(Float orgPrice) {
		this.orgPrice = orgPrice;
	}
	public Float getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(Float evaluate) {
		this.evaluate = evaluate;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
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
	
	
}
