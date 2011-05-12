/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.struts2.date.view;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.TagLibrary;

import com.ewcms.web.struts2.date.view.freemarker.tag.HeadModel;
import com.opensymphony.xwork2.util.ValueStack;

/**
 *功能说明

 * @author 周冬初
 *
 */
public class HeadTagLibrary implements TagLibrary {
    public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HeadModel(stack,req,res);
    }

    @SuppressWarnings("unchecked")
	public List<Class> getVelocityDirectiveClasses() {
        return new ArrayList<Class>();
    }
}
