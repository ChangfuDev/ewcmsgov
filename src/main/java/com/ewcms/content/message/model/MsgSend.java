/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 消息发送
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>userName:用户</li>
 * <li>title:标题</li>
 * <li>sendTime:发送时间</li>
 * <li>type:类型</li>
 * <li>status:状态</li>
 * <li>msgContents:内容对象集合</li>
 * <li>receiveUserNames:接收者</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "message_send")
@SequenceGenerator(name = "seq_message_send", sequenceName = "seq_message_send_id", allocationSize = 1)
public class MsgSend implements Serializable {

	private static final long serialVersionUID = 8470154441934582608L;

	@Id
    @GeneratedValue(generator = "seq_message_send",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "username", nullable = false)
	private String userName;
	@Column(name = "title", length = 200)
	private String title;
	@Column(name = "sendtime")
	private Date sendTime;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private MsgType type;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MsgStatus status;
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY, targetEntity = MsgContent.class)
	@JoinColumn(name = "send_id")
	@OrderBy(value = "id Desc")
	private List<MsgContent> msgContents = new ArrayList<MsgContent>();
	@Column(name = "receive_username")
	private String receiveUserNames;

	public MsgSend(){
		sendTime = new Date(Calendar.getInstance().getTime().getTime());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}
	
	public String getTypeDescription(){
		return type.getDescription();
	}

	public MsgStatus getStatus() {
		return status;
	}

	public void setStatus(MsgStatus status) {
		this.status = status;
	}
	
	public String getStatusDescription(){
		return status.getDescription();
	}
	
	public List<MsgContent> getMsgContents() {
		return msgContents;
	}

	public void setMsgContents(List<MsgContent> msgContents) {
		this.msgContents = msgContents;
	}

	public String getReceiveUserNames() {
		return receiveUserNames;
	}

	public void setReceiveUserNames(String receiveUserNames) {
		this.receiveUserNames = receiveUserNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MsgSend other = (MsgSend) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
