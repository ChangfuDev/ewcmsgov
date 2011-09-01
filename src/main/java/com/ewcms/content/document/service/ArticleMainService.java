/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.dao.ReviewProcessDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.content.document.util.ArticleUtil;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author 吴智俊
 */
@Service
public class ArticleMainService implements ArticleMainServiceable {

	@Autowired
	private WebPublishable webPublish;
	@Autowired
	private ArticleMainDAO articleMainDAO;
	@Autowired
	private ReviewProcessDAO reviewProcessDAO;
	
	public void setWebPublish(WebPublishable webPublish) {
		this.webPublish = webPublish;
	}

	public void setArticleMainDAO(ArticleMainDAO articleMainDAO){
		this.articleMainDAO = articleMainDAO;
	}
	
	@Override
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId) {
		return articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
	}

	@Override
	public void delArticleMain(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		if (isNotNull(articleMain.getReference()) && articleMain.getReference()){
			articleMain.setArticle(null);
		}
		articleMainDAO.remove(articleMain);
	}

	@Override
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		if (isNotNull(articleMain.getReference()) && articleMain.getReference()){
			articleMain.setArticle(null);
			articleMainDAO.remove(articleMain);
		}else{
			Article article = articleMain.getArticle();
			Assert.notNull(article);
			
			ArticleUtil.addOperateTrack(article, article.getStatusDescription(), userName, "删除到回收站");
			
			article.setDeleteFlag(true);
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}
	}

	@Override
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);
		
		ArticleUtil.addOperateTrack(article, article.getStatusDescription(), userName, "从回收站恢复");
		
		article.setStatus(ArticleStatus.REEDIT);
		article.setDeleteFlag(false);
		articleMain.setArticle(article);
		articleMainDAO.merge(articleMain);
	}

	@Override
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);
		if (article.getStatus() == ArticleStatus.DRAFT || article.getStatus() == ArticleStatus.REEDIT) {
			List<ReviewProcess> rpList = reviewProcessDAO.findReviewProcessByChannel(channelId);
			if (rpList == null || rpList.isEmpty()){
				ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), "发布版");
				article.setStatus(ArticleStatus.PRERELEASE);
			}else{
				ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), "提交审核");
				article.setStatus(ArticleStatus.REVIEW);
				article.setReviewProcessId(rpList.get(0).getId());
			}
			
			if (article.getPublished() == null) {
				article.setPublished(new Date(Calendar.getInstance().getTime().getTime()));
			}
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
			return true;
		}
		return false;
	}

//	@Override
//	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId) {
//		Assert.notNull(articleMainIds);
//		for (Long articleMainId : articleMainIds) {
//			submitReviewArticleMain(articleMainId, channelId);
//		}
//	}

	@Override
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId) {
		ArticleMain articleMain = null;
		Article article = null;
		for (Integer target_channelId : channelIds) {
			for (Long articleMainId : articleMainIds) {
				articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, source_channelId);
				if (isNull(articleMain)) continue;
				if (isNotNull(articleMain.getReference()) && articleMain.getReference()) continue;
				article = articleMain.getArticle();
				if (isNull(article)) continue;
				if (target_channelId != source_channelId) {
					Article target_article = new Article();

					target_article.setStatus(ArticleStatus.DRAFT);
					target_article.setPublished(null);
					if (article.getType() == ArticleType.TITLE){
						target_article.setUrl(article.getUrl());
					}else{
						target_article.setUrl(null);
					}
					target_article.setDeleteFlag(article.getDeleteFlag());

					List<Content> contents = article.getContents();
					List<Content> contents_target = new ArrayList<Content>();
					for (Content content : contents) {
						Content content_target = new Content();
						content_target.setDetail(content.getDetail());
						content_target.setPage(content.getPage());

						contents_target.add(content_target);
					}
					target_article.setContents(contents_target);

					target_article.setTitle(article.getTitle());
					target_article.setShortTitle(article.getShortTitle());
					target_article.setSubTitle(article.getSubTitle());
					target_article.setAuthor(article.getAuthor());
					target_article.setOrigin(article.getOrigin());
					target_article.setKeyword(article.getKeyword());
					target_article.setTag(article.getTag());
					target_article.setSummary(article.getSummary());
					target_article.setImage(article.getImage());
					target_article.setTopFlag(article.getTopFlag());
					target_article.setCommentFlag(article.getCommentFlag());
					target_article.setType(article.getType());
					target_article.setModified(new Date(Calendar.getInstance().getTime().getTime()));

					ArticleMain articleMain_new = new ArticleMain();
					articleMain_new.setArticle(target_article);
					articleMain_new.setChannelId(target_channelId);
					articleMainDAO.persist(articleMain_new);
				}
			}
		}
		return true;
	}

	@Override
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId) {
		ArticleMain articleMain = null;
		for (Integer target_channelId : channelIds) {
			for (Long articleMainId : articleMainIds) {
				articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, source_channelId);
				if (isNull(articleMain)) continue;
				if (target_channelId != source_channelId) {
					articleMain.setChannelId(target_channelId);
					articleMainDAO.merge(articleMain);
				}
			}
		}
		return true;
	}

	@Override
	public List<ArticleMain> findArticleMainByChannel(Integer channelId) {
		return articleMainDAO.findArticleMainByChannel(channelId);
	}

	@Override
	public void pubArticleMainByChannel(Integer channelId) throws PublishException {
		if (isNotNull(channelId)) {
			//TODO 判断频道是否需要审核。如果需要审核，抛出需要审核的提示；如果不需要审核，直接发布。
			webPublish.publishChannel(channelId, true);
		}
	}

	@Override
	public void reviewArticleMain(Long articleMainId, Integer channelId, Integer review, String description) {
		ArticleMain articleMain = null;
		Article article = null;
		Assert.notNull(articleMainId);
		articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		if (isNull(articleMain)) return;
		article = articleMain.getArticle();
		if (isNull(article)) return;
		//TODO 根据频道里审核流程判断是否还要审核。如果不需要再审核，就把文章状态改为预发布；如果还需要审核，还是审核状态。
		if (article.getStatus() == ArticleStatus.REVIEW) {
			ReviewProcess rp = reviewProcessDAO.findReviewProcessByIdAndChannel(article.getReviewProcessId(), channelId);
			if (review == 0){// 通过
				if (rp != null){
					ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), rp.getName() + ":<span style='color:blue;'>通过</span>");
					if (rp.getNextProcess() != null) {
						Long nextReviewProcessId = rp.getNextProcess().getId();
						article.setReviewProcessId(nextReviewProcessId);
//						ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), "通过");
					}else{
//						ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), "通过并可使发布");
						article.setStatus(ArticleStatus.PRERELEASE);
					}
				}else{
					ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), "审核流程已改变,此文章不能再进行审核");
					//TODO 文章处于异常状态
				}
				articleMain.setArticle(article);
				articleMainDAO.merge(articleMain);
			}else if (review == 1){// 不通过
				ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), rp.getName() + ":<span style='color:red;'>不通过</span>&nbsp;&nbsp;原因:" + description);
				if (rp != null){
					if (rp.getPrevProcess() != null){
						Long parentId = rp.getPrevProcess().getId();
						article.setReviewProcessId(parentId);
					}else{
						article.setStatus(ArticleStatus.REEDIT);
					}
				}else{
					//TODO 文章处于异常状态
					ArticleUtil.addOperateTrack(article, article.getStatusDescription(), EwcmsContextUtil.getUserName(), "审核流程已改变,此文章不能再进行审核");
				}
				articleMain.setArticle(article);
				articleMainDAO.merge(articleMain);
			}
		}
	}

	@Override
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
		if (articleMain == null){
			articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			Assert.notNull(articleMain);
			articleMain.setSort(sort);
			articleMainDAO.merge(articleMain);
		}else{
			ArticleMain articleMain_new = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			Assert.notNull(articleMain_new);
			articleMain_new.setSort(sort);
			if (isInsert == 0){//插入
				List<ArticleMain> articleMains = articleMainDAO.findArticleMainByChannelAndThanSort(channelId, sort, isTop);
				if (!articleMains.isEmpty()){
					for (ArticleMain articleMain_old : articleMains){
						if (articleMain_new.getId() == articleMain_old.getId()) continue;
						articleMain_old.setSort(articleMain_old.getSort() + 1);
						articleMainDAO.merge(articleMain_old);
					}
				}
				articleMainDAO.merge(articleMain_new);
			}else if (isInsert == 1){//替换
				if (articleMain_new.getId() != articleMain.getId()){
					articleMain.setSort(null);
					articleMainDAO.merge(articleMain);
					articleMainDAO.merge(articleMain_new);
				}
			}
		}
	}

	@Override
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
		if (articleMain == null) return false;
		return true;
	}

	@Override
	public void clearArticleMainSort(List<Long> articleMainIds, Integer channelId) {
		Assert.notNull(articleMainIds);
		for (Long articleMainId : articleMainIds){
			ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			Assert.notNull(articleMain);
			if (articleMain.getSort() != null){
				articleMain.setSort(null);
				articleMainDAO.merge(articleMain);
			}
		}
	}
}
