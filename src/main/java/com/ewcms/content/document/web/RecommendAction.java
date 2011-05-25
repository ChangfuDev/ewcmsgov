/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class RecommendAction extends ActionSupport {

	private static final long serialVersionUID = -7033704041589731104L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer articleRmcId;
	private Integer selectIds[];

	public Integer getArticleRmcId() {
		return articleRmcId;
	}

	public void setArticleRmcId(Integer articleRmcId) {
		this.articleRmcId = articleRmcId;
	}
	
	public Integer[] getSelectIds() {
		return selectIds;
	}

	public void setSelectIds(Integer[] selectIds) {
		this.selectIds = selectIds;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String save(){
		documentFac.saveRecommend(getArticleRmcId(), getSelectIds());
		return NONE;
	}
	
	public String delete(){
		documentFac.deleteRecommend(getArticleRmcId(), getSelectIds());
		return NONE;
	}
	
	public void up(){
		try{
			if (getArticleRmcId() != null && getSelectIds() != null){
				documentFac.upRecommend(getArticleRmcId(), getSelectIds());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void down(){
		try{
			if (getArticleRmcId() != null && getSelectIds() != null){
				documentFac.downRecommend(getArticleRmcId(), getSelectIds());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
