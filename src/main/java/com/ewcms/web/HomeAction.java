/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.web.context.EwcmsContextHolder;

/**
 * 首页Action
 *
 * @author 周冬初
 */
@Controller("home")
public class HomeAction extends EwcmsBaseAction {
    
    private Integer siteId;
    private String siteName;
    private String realName;
    private boolean hasSite = true;
    
	@Autowired
	private SiteFac siteFac;
	
	@Autowired
	private SecurityFacable securityFac;

	/**
     * 得到操作站点
     * 
     * @param id 站点编号
     * @return 操作站点
     * TODO 得到用户所属组织，通过组织得到站点。
     */
    private Site getSite(Integer id) {
        if(id != null){
            Site site =  siteFac.getSite(siteId);
            if(site == null){
                addActionError("站点不存在");
                hasSite = false;
                return new Site();    
            }
            return site;
        }else{
            List<Site> list= siteFac.getSiteListByOrgans(new Integer[]{}, true);
            if(list == null || list.isEmpty()){
                hasSite = false;
                return new Site();
            }
           return list.get(0);
        }
    }

    /**
     * 初始站点到访问上下文中当，提供全局访问
     * 
     * @param site
     */
    private void initSiteInContext(Site site){
	    EwcmsContextHolder.getContext().setSite(site);
	}
	
	public String execute() {
	    
	    realName = securityFac.getCurrentUserInfo().getName();
	    
		Site site = getSite(siteId);
		if(site != null){
		    setSiteName(site.getSiteName());
		    initSiteInContext(site);
		}
		
		return SUCCESS;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    
    public String getRealName(){
        return realName;
    }
    
    public Boolean getHasSite(){
        return hasSite;
    }
}
