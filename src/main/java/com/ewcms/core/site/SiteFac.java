/**
 * 
 */
package com.ewcms.core.site;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Organ;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.service.ChannelService;
import com.ewcms.core.site.service.OrganService;
import com.ewcms.core.site.service.SiteService;
import com.ewcms.core.site.service.TemplateService;
import com.ewcms.core.site.service.TemplateSourceService;
import com.ewcms.web.util.TreeNode;

/**
 * @author 周冬初
 *
 */
@Service
public class SiteFac{
	@Autowired
	private SiteService siteService;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private OrganService organService;	
	@Autowired
	private TemplateSourceService templateSourceService;
	
	
	public List<Site> getSiteListByOrgans(Integer[] organs, Boolean publicenable) {
		return siteService.getSiteListByOrgans(organs, publicenable);
	}


	
	public List<TreeNode> getOrganSiteTreeList(Integer organId) {
		return siteService.getOrganSiteTreeList(organId);
	}


	
	public List<TreeNode> getOrganSiteTreeList(Integer parentId, Integer organId) {
		return siteService.getOrganSiteTreeList(parentId, organId);
	}


	
	public List<TreeNode> getCustomerSiteTreeList() {
		return siteService.getCustomerSiteTreeList();
	}


	
	public List<TreeNode> getCustomerSiteTreeList(Integer parentId) {
		return siteService.getCustomerSiteTreeList(parentId);
	}


	
	public Integer addSite(Integer parentId, String siteName,Integer organId){
		return siteService.addSite(parentId,siteName,organId);
	}

	
	public void delSiteBatch(List<Integer> idList) {
		siteService.delSiteBatch(idList);		
	}

	
	public Integer updSite(Site vo) {
		return siteService.updSite(vo);
	}

	
	public void delSite(Integer id) {
		siteService.delSite(id);		
	}

	
	public Site getSite(Integer id) {
		return siteService.getSite(id);
	}

	
	public Template getTemplate(Integer id) {
		return templateService.getTemplate(id);
	}

	
	public Integer addTemplate(Template vo) {
		return templateService.addTemplate(vo);
	}

	
	public Integer updTemplate(Template vo) {
		return templateService.updTemplate(vo);
	}

	
	public void delTemplateBatch(List<Integer> idList) {
		templateService.delTemplateBatch(idList);		
	}

	
	public void delTemplate(Integer id) {
		templateService.delTemplate(id);		
	}


	
	public List<Template> getTemplateList() {
		return templateService.getTemplateList();
	}

	
	public List<ChannelNode> getChannelChildren(Integer parentId,
			Boolean publicenable) {
		return channelService.getChannelChildren(parentId, publicenable);
	}


	
	public Integer addChannel(Integer parentId, String name) {
		return channelService.addChannel(parentId, name);
	}

	
	public void renameChannel(Integer id, String name) {
		channelService.renameChannel(id, name);
	}

	
	public Integer updChannel(Channel vo) {
		return channelService.updChannel(vo);
	}

	
	public void delChannel(Integer id) {
		channelService.delChannel(id);
	}

	
	public Channel getChannel(Integer id) {
		return channelService.getChannel(id);
	}	
	
	public Channel getChannelRoot() {
		return channelService.getChannelRoot();
	}
	
	public ChannelNode channelNodeRoot() {
		return channelService.channelNodeRoot();
	}
	
	public List<Template> getTemplaeTreeList(Boolean channelEnable) {
		return templateService.getTemplaeTreeList(channelEnable);
	}

	
	public List<Template> getTemplaeTreeList(Integer parentId,
			Boolean channelEnable) {
		return templateService.getTemplaeTreeList(parentId, channelEnable);
	}

	
	public List<Template> getTemplaeTreeList(Integer parentId,
			String channelName) {
		return templateService.getTemplaeTreeList(parentId, channelName);
	}

	
	public void updSiteParent(Integer organId,Integer parentId, Integer newParentId) {
		siteService.updSiteParent(organId,parentId, newParentId);
		
	}

	
	public Integer addOrgan(Integer parentId, String organName) {
		return organService.addOrgan(parentId, organName);
	}
	
	public Integer updOrgan(Organ vo){
		return organService.updOrgan(vo);
	}
	
	public void delOrgan(Integer id){
		organService.delOrgan(id);
	}
	public Organ getOrgan(Integer id){
		return organService.getOrgan(id);
	}
	
	public Integer saveOrganInfo(Organ vo){
		return organService.saveOrganInfo(vo);	
	}

	
	public List<TreeNode> getOrganTreeList() {
		return organService.getOrganTreeList();
	}

	
	public List<TreeNode> getOrganTreeList(Integer parentId) {
		return organService.getOrganTreeList(parentId);
	}

	
	public TemplateSource getTemplateSource(Integer id) {
		return templateSourceService.getTemplateSource(id);
	}

	
	public Integer addTemplateSource(TemplateSource vo) {
		return templateSourceService.addTemplateSource(vo);
	}

	
	public Integer updTemplateSource(TemplateSource vo) {
		return templateSourceService.updTemplateSource(vo);
	}

	
	public void delTemplateSource(Integer id) {
		templateSourceService.delTemplateSource(id);	
	}



	
	public List<TemplateSource> getTemplaeSourceTreeList(Boolean channelEnable) {
		return templateSourceService.getTemplaeSourceTreeList(channelEnable);
	}

	
	public List<TemplateSource> getTemplaeSourceTreeList(Integer parentId,
			Boolean channelEnable) {
		return templateSourceService.getTemplaeSourceTreeList(parentId, channelEnable);
	}

	
	public Template channelTemplate(String tplName) {
		return templateService.channelTemplate(tplName);
	}

	
	public Template channelTPLRoot() {
		return templateService.channelTPLRoot();
	}

	
	public TemplateSource channelTemplateSource(String srcName) {
		return templateSourceService.channelTemplateSource(srcName);
	}

	
	public TemplateSource channelSRCRoot() {
		return templateSourceService.channelSRCRoot();
	}
}
