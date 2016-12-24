package com.p2p.model.sys;

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

//用户认证类
@Entity
@Table(name = "p2p_Authz")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = { "userInfo"})
public class Authz {
	// IDAuthzId
	@Id
	@GeneratedValue
	@Column(name = "authzId")
	private Integer authzId;
	// 用户真实姓名
	@Column(name = "realName", nullable = false)
	private String realName;
	// 用户身份证号
	@Column(name = "idCardNO", nullable = false)
	private String idCardNO;
	//用户类
	@OneToOne(cascade = CascadeType.ALL, mappedBy="authz",fetch = FetchType.LAZY)	
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}


	public Integer getAuthzId() {
		return authzId;
	}

	public void setAuthzId(Integer authzId) {
		this.authzId = authzId;
	}
	
	

}
