/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.history;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class SelectIdAction extends ActionSupport {

	private static final long serialVersionUID = -7033704041589731104L;

	private Integer articleId;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
