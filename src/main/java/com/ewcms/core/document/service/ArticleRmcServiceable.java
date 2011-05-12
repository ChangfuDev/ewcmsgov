/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ewcms.core.document.model.Article;
import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.core.document.model.Recommend;
import com.ewcms.core.document.model.Related;
import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author 吴智俊
 */
public interface ArticleRmcServiceable {
	/**
	 * 新增文章
	 * 
	 * @param article
	 * @param channelId
	 * @param published
	 * @return
	 */
	public Integer addArticleRmc(Article article, Integer channelId, Date published);
	
	/**
	 * 修改文章
	 * 
	 * @param articleRmc
	 * @param article
	 * @param channelId
	 * @param published
	 * @return
	 */
	public Integer updArticleRmc(Integer articleRmcId, Article article, Integer channelId, Date published);

	/**
	 * 查询文章
	 * 
	 * @param articleId
	 * @return
	 */
	public ArticleRmc getArticleRmc(Integer articleId);

	/**
	 * 删除文章
	 * 
	 * @param articleRmcId
	 */
	public void delArticleRmc(Integer articleRmcId);

	/**
	 * 删除文章到回收站
	 * 
	 * @param articleRmcId
	 * @param userName
	 */
	public void delArticleRmcToRecycleBin(Integer articleRmcId, String userName);

	/**
	 * 恢复文章
	 * 
	 * @param articleRmcId
	 * @param userName
	 */
	public void restoreArticleRmc(Integer articleRmcId, String userName);

	/**
	 * 预发布文章(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleRmcId
	 * @return
	 */
	public Boolean preReleaseArticleRmc(Integer articleRmcId);
	
	/**
	 * 预发布文章(对任务状态的文章进行发布)
	 * 
	 * @param articleRmcIds 文章编号列表
	 */
	public void preReleaseArticleRmcs(List<Integer> articleRmcIds);

	/**
	 * 拷贝文章
	 * 
	 * @param articleRmcIds
	 * @param channelIds
	 * @return
	 */
	public Boolean copyArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds);

	/**
	 * 移动文章
	 * 
	 * @param articleRmcIds
	 * @param channelIds
	 * @return
	 */
	public Boolean moveArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds);

	/**
	 * 查询历史内容转换到页面显示
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Map<String, Object>> findContnetHistoryToPage(Integer articleId);

	/**
	 * 通过频道编号查询文章
	 * 
	 * @param channelId
	 * @return
	 */
	public List<ArticleRmc> findArticleRmcByChannelId(Integer channelId);

	/**
	 * 查询推荐文章
	 * 
	 * @param articleRmcId
	 * @return
	 */
	public List<Recommend> findRecommendByArticleId(Integer articleRmcId);

	/**
	 * 查询相关文章
	 * 
	 * @param articleRmcId
	 * @return
	 */
	public List<Related> findRelatedByArticleId(Integer articleRmcId);
	
	/**
	 * 文章与人群关联
	 * 
	 * @param articleRmcId 文章编号
	 * @param citizenIds 人群编号集合
	 */
	public void addArticleRmcToCitizen(Integer articleRmcId, Integer[] citizenIds);
	
	/**
	 * 发布文章
	 * @param channelId 频道编号
	 * @throws ReleaseException
	 */
	public void pubChannel(Integer channelId) throws ReleaseException;
}
