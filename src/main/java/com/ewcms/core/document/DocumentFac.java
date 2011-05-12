/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.document.model.Article;
import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.core.document.model.Citizen;
import com.ewcms.core.document.model.Recommend;
import com.ewcms.core.document.model.Related;
import com.ewcms.core.document.service.ArticleRmcServiceable;
import com.ewcms.core.document.service.CitizenServiceable;
import com.ewcms.core.document.service.RecommendServiceable;
import com.ewcms.core.document.service.RelatedServiceable;
import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author 吴智俊
 */
@Service()
public class DocumentFac implements DocumentFacable {
	
	@Autowired
	private ArticleRmcServiceable articleRmcService;
	@Autowired
	private RelatedServiceable relatedService;
	@Autowired
	private RecommendServiceable recommendService;
	@Autowired
	private CitizenServiceable citizenService;
	
	//------------------文章------------------------------//
	@Override
	public Integer addArticleRmc(Article article, Integer channelId, Date published) {
		return articleRmcService.addArticleRmc(article, channelId, published);
	}

	@Override
	public void delArticleRmc(Integer articleRmcId) {
		articleRmcService.delArticleRmc(articleRmcId);
	}

	@Override
	public ArticleRmc getArticleRmc(Integer articleRmcId) {
		return articleRmcService.getArticleRmc(articleRmcId);
	}

	@Override
	public Integer updArticleRmc(Integer articleRmcId, Article article, Integer channelId, Date published) {
		return articleRmcService.updArticleRmc(articleRmcId, article, channelId, published);
	}

	@Override
	public void delArticleRmcToRecycleBin(Integer articleRmcId, String userName) {
		articleRmcService.delArticleRmcToRecycleBin(articleRmcId, userName);
	}

	@Override
	public void restoreArticleRmc(Integer articleRmcId, String userName) {
		articleRmcService.restoreArticleRmc(articleRmcId, userName);
	}

	@Override
	public List<Map<String,Object>> findContnetHistoryToPage(Integer articleId){
		return articleRmcService.findContnetHistoryToPage(articleId);
	}
	
	@Override
	public List<ArticleRmc> findArticleRmcByChannel(Integer channelId){
		return articleRmcService.findArticleRmcByChannelId(channelId);
	}
	
	@Override
	public List<Related> findRelatedByArticleRmcId(Integer articleRmcId){
		return articleRmcService.findRelatedByArticleId(articleRmcId);
	}
	
	@Override
	public List<Recommend> findRecommendByArticleRmcId(Integer articleRmcId){
		return articleRmcService.findRecommendByArticleId(articleRmcId);
	}
	
	@Override
	public Boolean preReleaseArticleRmc(Integer articleRmcId){
		return articleRmcService.preReleaseArticleRmc(articleRmcId);
	}
	
	@Override
	public void preReleaseArticleRmcs(List<Integer> articleRmcIds){
		articleRmcService.preReleaseArticleRmcs(articleRmcIds);
	}

	@Override
	public Boolean copyArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds){
		return articleRmcService.copyArticleRmcToChannel(articleRmcIds, channelIds);
	}
	
	@Override
	public Boolean moveArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds){
		return articleRmcService.moveArticleRmcToChannel(articleRmcIds, channelIds);
	}
	
	//--------------------相关文章-----------------------------//
	@Override
	public void saveRelated(Integer articleId, Integer[] relatedArticleIds) {
		relatedService.saveRelated(articleId, relatedArticleIds);
	}

	@Override
	public void deleteRelated(Integer articleId, Integer[] relatedArticleIds) {
		relatedService.deleteRelated(articleId, relatedArticleIds);
	}

	@Override
	public void upRelated(Integer articleId, Integer[] relatedArticleIds) {
		relatedService.upRelated(articleId, relatedArticleIds);
	}
	
	@Override
	public void downRelated(Integer articleId, Integer[] relatedArticleIds) {
		relatedService.downRelated(articleId, relatedArticleIds);
	}

	//--------------------推荐文章----------------------------//
	@Override
	public void saveRecommend(Integer articleId, Integer[] recommendArticleIds) {
		recommendService.saveRecommend(articleId, recommendArticleIds);
	}

	@Override
	public void deleteRecommend(Integer articleId, Integer[] recommendArticleIds) {
		recommendService.deleteRecommend(articleId, recommendArticleIds);
	}

	@Override
	public void upRecommend(Integer articleId, Integer[] recommendArticleIds) {
		recommendService.upRecommend(articleId, recommendArticleIds);
	}
	
	@Override
	public void downRecommend(Integer articleId, Integer[] recommendArticleIds) {
		recommendService.downRecommend(articleId, recommendArticleIds);
	}
	
	@Override
	public Integer addCitizen(Citizen citizen) {
		return citizenService.addCitizen(citizen);
	}

	@Override
	public void delCitizen(Integer citizenId) {
		citizenService.delCitizen(citizenId);
	}

	@Override
	public Citizen getCitizen(Integer citizenId) {
		return citizenService.getCitizen(citizenId);
	}

	@Override
	public Integer updCitizen(Citizen citizen) {
		return citizenService.updCitizen(citizen);
	}

	@Override
	public void addArticleRmcToCitizen(Integer articleRmcId, Integer[] citizenIds) {
		articleRmcService.addArticleRmcToCitizen(articleRmcId, citizenIds);
	}

	@Override
	public void pubChannel(Integer channelId) throws ReleaseException {
		articleRmcService.pubChannel(channelId);
	}
}
