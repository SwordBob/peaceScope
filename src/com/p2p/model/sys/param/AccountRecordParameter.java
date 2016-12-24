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
public class AccountRecordParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = -6335587468796360403L;
	private String property;
	private String $like_tradeTime;
	private Integer $eq_accountRecordId;
	private String $like_tradeProcesses;
	private Integer $eq_accountId;
	
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}

	public String get$like_tradeTime() {
		return $like_tradeTime;
	}

	public void set$like_tradeTime(String $like_tradeTime) {
		this.$like_tradeTime = $like_tradeTime;
	}

	public Integer get$eq_accountRecordId() {
		return $eq_accountRecordId;
	}

	public void set$eq_accountRecordId(Integer $eq_accountRecordId) {
		this.$eq_accountRecordId = $eq_accountRecordId;
	}

	public String get$like_tradeProcesses() {
		return $like_tradeProcesses;
	}

	public void set$like_tradeProcesses(String $like_tradeProcesses) {
		this.$like_tradeProcesses = $like_tradeProcesses;
	}

	public Integer get$eq_accountId() {
		return $eq_accountId;
	}

	public void set$eq_accountId(Integer $eq_accountId) {
		this.$eq_accountId = $eq_accountId;
	}
	
}
