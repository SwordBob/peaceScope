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
public class ReceivePlanParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = -6335587468796360403L;
	private String property;
	private String $like_planDate;
	private Integer $eq_receivePlanId;
	private String $like_receiveState;
	private Integer $eq_borrowId;
	
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}

	public String get$like_planDate() {
		return $like_planDate;
	}

	public void set$like_planDate(String $like_planDate) {
		this.$like_planDate = $like_planDate;
	}

	public Integer get$eq_receivePlanId() {
		return $eq_receivePlanId;
	}

	public void set$eq_receivePlanId(Integer $eq_receivePlanId) {
		this.$eq_receivePlanId = $eq_receivePlanId;
	}

	public String get$like_receiveState() {
		return $like_receiveState;
	}

	public void set$like_receiveState(String $like_receiveState) {
		this.$like_receiveState = $like_receiveState;
	}

	public Integer get$eq_borrowId() {
		return $eq_borrowId;
	}

	public void set$eq_borrowId(Integer $eq_borrowId) {
		this.$eq_borrowId = $eq_borrowId;
	}
	
}
