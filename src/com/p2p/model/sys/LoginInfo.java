package com.p2p.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import core.extjs.DateTimeSerializer;
import core.extjs.ExtJSBaseParameter;

//登录检测表
@Entity
@Table(name = "p2p_loginInfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoginInfo extends ExtJSBaseParameter{
	//表ID
	@Id
	@GeneratedValue
	@Column(name = "loginId")
	private Integer loginId;
	//登录时间
	@Column(name = "loginTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;
	
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
