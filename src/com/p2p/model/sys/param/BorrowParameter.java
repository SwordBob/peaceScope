package com.p2p.model.sys.param;

import java.util.Date;

import javax.persistence.Column;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import core.extjs.DateTimeSerializer;
import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class BorrowParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 4462121985297150686L;
	//未声明$的用于dto临时存值
	private Date repaymentDate;
	//private Integer repaymentStateId;
	private Date repaymentEnd;
	private Integer otherInfoId;
//	private String $like_name;
	private Integer $eq_repayId;
	//eq是具体equal查询，like是模糊查询
	private Integer $eq_userId;
	private String $like_title;
	private String $like_repaymentEnd;
	private String $like_state;
	// private String p2pTypeImagePath;
	private Integer userInfoId;
	private String userInfoName;
	//private String $like_userInfoPhone;
	private String userInfoPhone;

	// 抵押车辆编号
	private Integer carInfoId;
	// 车辆品牌
	private String carBrand;
	// 车牌号
	private String carId;
	// 公里数
	private Integer mileage;	
    // 购买价格
	private Float orgPrice;
	// 抵押价格
	private Float evaluate;
	// 审核时间
	private Date auditDate;
	// 审核人
	private String auditor;
	// 已还期数
	/*private Integer payet;
	// 应还金额
	private Float estimate;
	// 已还金额
	private Float actual;
	// 逾期罚款
	private Float penalty;
*/
	public String get$like_state() {
		return $like_state;
	}

	public void set$like_state(String $like_state) {
		this.$like_state = $like_state;
	}

	public Integer get$eq_userId() {
		return $eq_userId;
	}

	public void set$eq_userId(Integer integer) {
		this.$eq_userId = integer;
	}
//
//	public String get$like_epcId() {
//		return $like_epcId;
//	}
//
//	public void set$like_epcId(String $like_epcId) {
//		this.$like_epcId = $like_epcId;
//	}
//
//	public String get$like_name() {
//		return $like_name;
//	}
//
//	public void set$like_name(String $like_name) {
//		this.$like_name = $like_name;
//	}
//
//	public Integer getRepaymentStateId() {
//		return repaymentStateId;
//	}
//
//	public void setRepaymentStateId(Integer repaymentStateId) {
//		this.repaymentStateId = repaymentStateId;
//	}
//
	public Integer get$eq_repayId() {
		return $eq_repayId;
	}

	public void set$eq_repayId(Integer $eq_repayId) {
		this.$eq_repayId = $eq_repayId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public Integer getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(Integer userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getUserInfoName() {
		return userInfoName;
	}

	public void setUserInfoName(String userInfoName) {
		this.userInfoName = userInfoName;
	}

	/*public Integer getRepaymentStateId() {
		return repaymentStateId;
	}

	public void setRepaymentStateId(Integer repaymentStateId) {
		this.repaymentStateId = repaymentStateId;
	}*/

	public Integer getCarInfoId() {
		return carInfoId;
	}

	public void setCarInfoId(Integer carInfoId) {
		this.carInfoId = carInfoId;
	}

	public String getUserInfoPhone() {
		return userInfoPhone;
	}

	public void setUserInfoPhone(String userInfoPhone) {
		this.userInfoPhone = userInfoPhone;
	}

	public String get$like_title() {
		return $like_title;
	}

	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getRepaymentEnd() {
		return repaymentEnd;
	}

	public void setRepaymentEnd(Date repaymentEnd) {
		this.repaymentEnd = repaymentEnd;
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

	public String get$like_repaymentEnd() {
		return $like_repaymentEnd;
	}

	public void set$like_repaymentEnd(String $like_repaymentEnd) {
		this.$like_repaymentEnd = $like_repaymentEnd;
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

	public Integer getOtherInfoId() {
		return otherInfoId;
	}

	public void setOtherInfoId(Integer otherInfoId) {
		this.otherInfoId = otherInfoId;
	}

	/*
	public Integer getPayet() {
		return payet;
	}

	public void setPayet(Integer payet) {
		this.payet = payet;
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
*/
	/*
	 * public String getRepaymentDate() { return repaymentDate; }
	 * 
	 * public void setRepaymentDate(String repaymentDate) { this.repaymentDate = repaymentDate; }
	 */

}
