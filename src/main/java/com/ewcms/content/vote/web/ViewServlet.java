/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.content.vote.VoteFacable;

/**
 * 显示调查投票内容 
 * 
 * @author wu_zhijun
 */
public class ViewServlet extends HttpServlet {

	private static final long serialVersionUID = 5728574569482918331L;
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream out = null;
		StringBuffer output = new StringBuffer();
		try{
			String questionnaireId = req.getParameter("id");
			if (questionnaireId == null){
				output.append("没有投票！");
			}else{
		    	ServletContext application = getServletContext(); 
		    	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
		    	VoteFacable voteFac = (VoteFacable) wac.getBean("voteFac");
		    	
		    	StringBuffer js = voteFac.getQuestionnaireViewToHtml(new Long(questionnaireId), getServletContext().getServletContextName());
		    	output.append(js.toString());
			}
			out = resp.getOutputStream();
	    	resp.setCharacterEncoding("utf-8");
	    	resp.setContentType("text/html; charset=utf-8");
	    	out.write(output.toString().getBytes());
	    	out.flush();
		}finally{
    		if (out != null){
    			out.close();
    			out = null;
    		}
		}
    }
}
