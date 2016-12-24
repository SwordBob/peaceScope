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


@Entity
@Table(name = "p2p_MyMessage")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyMessage {
	//信息ID
	@Id
	@GeneratedValue
	@Column(name = "messageId")
	private Integer messageId;
	//信息内容
	@Column(name = "messageContent")
	private String messageContent;
	//收信日期
	@Column(name = "receiveDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receiveDate;
	//信息状态 1未读 0 已读
	@Column(name = "messageState")
	private Integer messageState;
	
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public Integer getMessageState() {
		return messageState;
	}
	public void setMessageState(Integer messageState) {
		this.messageState = messageState;
	}
	
	
}
