/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.plugin.message.manager.dao.MsgContentDAO;
import com.ewcms.plugin.message.manager.dao.MsgReceiveDAO;
import com.ewcms.plugin.message.manager.dao.MsgSendDAO;
import com.ewcms.plugin.message.model.MsgContent;
import com.ewcms.plugin.message.model.MsgReceive;
import com.ewcms.plugin.message.model.MsgReceiveUser;
import com.ewcms.plugin.message.model.MsgSend;
import com.ewcms.plugin.message.model.MsgSend.Type;
import com.ewcms.plugin.message.model.MsgStatus;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Service
public class MsgSendService implements MsgSendServiceable {

	@Autowired
	private MsgSendDAO msgSendDAO;
	@Autowired
	private MsgReceiveDAO msgReceiveDAO;
	@Autowired
	private MsgContentDAO msgContentDAO;
	@Autowired
	private UserServiceable userService;

	@Override
	public Long addMsgSend(MsgSend msgSend, String content, List<String> userNames) {
		msgSend.setUserName(EwcmsContextUtil.getUserName());
		msgSend.setStatus(MsgStatus.FAVORITE);
		MsgContent msgContent = new MsgContent();
		msgContent.setTitle(msgSend.getTitle());
		msgContent.setDetail(content);
		List<MsgContent> msgContents = new ArrayList<MsgContent>();
		msgContents.add(msgContent);
		msgSend.setMsgContents(msgContents);
		
		if (msgSend.getType() == Type.GENERAL){
			List<MsgReceiveUser> msgReceiveUsers = new ArrayList<MsgReceiveUser>();
			MsgReceiveUser msgReceiveUser;
			for (String userName : userNames){
				if (userName.equals(msgSend.getUserName())) continue;
				msgReceiveUser = new MsgReceiveUser();
				msgReceiveUser.setUserName(userName);
				msgReceiveUser.setRealName(findUserRealNameByUserName(userName));
				msgReceiveUsers.add(msgReceiveUser);
			}
			msgSend.setMsgReceiveUsers(msgReceiveUsers);
		}else if (msgSend.getType() == Type.NOTICE){
			msgSend.setMsgReceiveUsers(null);
		}else if (msgSend.getType() == Type.SUBSCRIPTION){
			msgSend.setMsgReceiveUsers(null);
		}
		
		msgSendDAO.persist(msgSend);
		msgSendDAO.flush(msgSend);
		
		if (msgSend.getType() == Type.GENERAL){
			for (String userName : userNames){
				if (userName.equals(msgSend.getUserName())) continue;
				MsgReceive msgReceive = new MsgReceive();
				msgReceive.setUserName(userName);
				msgReceive.setSendUserName(EwcmsContextUtil.getUserName());
				msgReceive.setStatus(MsgStatus.FAVORITE);
				msgReceive.setMsgContent(msgContent);
				msgReceiveDAO.persist(msgReceive);
			}		
		}
		
		return msgSend.getId();
	}

	@Override
	public Long updMsgSend(MsgSend send) {
		return null;
	}

	@Override
	public void delMsgSend(Long msgSendId) {
		msgSendDAO.removeByPK(msgSendId);
	}

	@Override
	public MsgSend findMsgSend(Long msgSendId) {
		return msgSendDAO.get(msgSendId);
	}

	@Override
	public List<MsgSend> findMsgSendByUserName() {
		return msgSendDAO.findMsgSendByUserName(EwcmsContextUtil.getUserName());
	}

	@Override
	public Long addSubscription(Long msgSendId, String title, String detail) {
		MsgSend msgSend = msgSendDAO.findMsgSendByUserNameAndId(EwcmsContextUtil.getUserName(), msgSendId);
		Assert.notNull(msgSend);
		if (msgSend.getType() == Type.SUBSCRIPTION){
			List<MsgContent> msgContents = msgSend.getMsgContents();
			
			MsgContent msgContent = new MsgContent();
			msgContent.setTitle(title);
			msgContent.setDetail(detail);
			
			msgContents.add(msgContent);
			msgSend.setMsgContents(msgContents);
			
			msgSendDAO.merge(msgSend);
			
			List<MsgReceiveUser> msgReceiveUsers = msgSend.getMsgReceiveUsers();
			for (MsgReceiveUser msgReceiveUser : msgReceiveUsers){
				MsgReceive msgReceive = new MsgReceive();
				msgReceive.setMsgContent(msgContent);
				msgReceive.setSendUserName(EwcmsContextUtil.getUserName());
				msgReceive.setSubscription(true);
				msgReceive.setUserName(msgReceiveUser.getUserName());
				msgReceive.setStatus(MsgStatus.FAVORITE);
				msgReceiveDAO.merge(msgReceive);
			}
			return msgSend.getId();
		}
		return null;
	}

	@Override
	public void delSubscription(Long msgContentId) {
		msgContentDAO.removeByPK(msgContentId);
	}

//	@Override
//	public List<MsgSend> findMsgSendByGeneral(Integer row) {
//		return msgSendDAO.findMsgSendByType(Type.GENERAL, row);
//	}

	@Override
	public List<MsgSend> findMsgSendByNotice(Integer row) {
		List<MsgSend> notices = new ArrayList<MsgSend>();
		List<MsgSend> noticeMessages = msgSendDAO.findMsgSendByType(Type.NOTICE, row);
		if (noticeMessages == null || noticeMessages.isEmpty()) return notices;
		for (MsgSend msgSend : noticeMessages){
			msgSend.setMsgReceiveUsers(null);
			notices.add(msgSend);
		}
		return notices;
	}

	@Override
	public List<MsgSend> findMsgSendBySubscription(Integer row) {
		List<MsgSend> subscriptions = new ArrayList<MsgSend>();
		List<MsgSend> subscriptionMessages = msgSendDAO.findMsgSendByType(Type.SUBSCRIPTION, row);
		if (subscriptionMessages == null || subscriptionMessages.isEmpty()) return subscriptions;
		for (MsgSend msgSend : subscriptionMessages){
			msgSend.setMsgReceiveUsers(null);
			subscriptions.add(msgSend);
		}
		return subscriptions;
	}

	@Override
	public String subscribeMsg(Long msgSendId) {
		MsgSend msgSend = msgSendDAO.get(msgSendId);
		if (msgSend.getType() == Type.SUBSCRIPTION){
			String receiveUserName = EwcmsContextUtil.getUserName();
			String realName = userService.getCurrentUserInfo().getName();
			String sendUserName = msgSend.getUserName();
			if (receiveUserName.equals(sendUserName)){
				return "own";
			}
			if (!msgSendDAO.findUserHaveSubscribedByUserName(msgSendId, receiveUserName)){
				List<MsgReceiveUser> msgReceiveUsers = msgSend.getMsgReceiveUsers();
				MsgReceiveUser msgReceiveUser = new MsgReceiveUser();
				msgReceiveUser.setUserName(receiveUserName);
				msgReceiveUser.setRealName(realName);
				msgReceiveUsers.add(msgReceiveUser);
				msgSend.setMsgReceiveUsers(msgReceiveUsers);
				msgSendDAO.merge(msgSend);
				return "true";
			}else{
				return "exist";
			}
		}else{
			return "false";
		}
	}
	
	private String findUserRealNameByUserName(String userName){
		User user = userService.getUser(userName);
		return user.getUserInfo().getName();
	}
}
