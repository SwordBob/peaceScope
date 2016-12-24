package com.p2p.model.sys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.p2p.model.sys.param.ContactsParameter;

@Entity
@Table(name = "p2p_contacts")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = { "userInfo"})
public class Contacts extends ContactsParameter{
	// IDAuthzId
	@Id
	@GeneratedValue
	@Column(name = "contactId")
	private Integer contactId;
	//紧急联系人姓名
	@Column(name = "contactName", nullable = true)
	private String contactName;
	//紧急联系人电话
	@Column(name = "contactPhone", nullable = true)
	private String contactPhone;
	//用户类
	@OneToOne(cascade = CascadeType.ALL,mappedBy="contacts", fetch = FetchType.LAZY)
	private UserInfo userInfo;
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
