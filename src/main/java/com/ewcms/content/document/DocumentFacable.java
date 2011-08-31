/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document;

import java.util.Date;
import java.util.List;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Relation;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.publication.PublishException;

/**
 *
 * @author 吴智俊
 */
public interface DocumentFacable {
	/**
	 * 新增文章分类属性
	 * 
	 * @param articleCategory 文章分类属性对象
	 * @return Integer 文章分类属性编号
	 */
	public Integer addArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 修改文章分类属性
	 * 
	 * @param articleCategory 文章分类属性对象
	 * @return Integer 文章分类属性编号
	 */
	public Integer updArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 删除文章分类属性
	 * 
	 * @param articleCategoryId 文章分类属性编号
	 */
	public void delArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询文章分类属性
	 * 
	 * @param articleCategoryId 文章分类属性编号
	 * @return ArticleCategory 文章分类属性对象
	 */
	public ArticleCategory findArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询所有文章分类属性集合
	 * 
	 * @return List 文章分类属性对象集合
	 */
	public List<ArticleCategory> findArticleCategoryAll();
	
	/**
	 * 查询文章主体
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @return ArticleMain 文章主体对象
	 */
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId);

	/**
	 * 删除文章主体
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 */
	public void delArticleMain(Long articleMainId, Integer channelId);

	/**
	 * 删除文章主体到回收站
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param userName 操作用户
	 */
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName);

	/**
	 * 恢复文章主体
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param userName 操作用户
	 */
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName);

	/**
	 * 提交审核文章主体(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @return Boolean true:提交成功,false:提交失败
	 */
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId);
	
	/**
	 * 提交审核文章主体(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelId 频道编号
	 */
	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId);

	/**
	 * 拷贝文章主体
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelIds 频道编号集合
	 * @return Boolean true:拷贝成功,false:拷贝失败
	 */
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);

	/**
	 * 移动文章主体
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelIds 频道编号集合
	 * @return Boolean true:移动成功,false:移动失败
	 */
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);

	/**
	 * 查询文章主体集合
	 * 
	 * @param channelId 频道编号
	 * @return List 文章主体集合
	 */
	public List<ArticleMain> findArticleMainByChannel(Integer channelId);

	/**
	 * 发布文章主体
	 * 
	 * @param channelId 频道编号
	 * @throws ReleaseException
	 */
	public void pubArticleMainByChannel(Integer channelId) throws PublishException;
	
	/**
	 * 审核文章主体
	 * 
	 * @param articleMainIds 文章主体集合
	 * @param review 审核标志(0:通过,1:未通过)
	 * @param eauthor 审核人
	 * @param description 描述
	 */
	public void reviewArticleMain(List<Long> articleMainIds, Integer channelId, Integer review, String eauthor, String description);
	
	/**
	 * 文章主体进行排序
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param sort 排序号
	 * @param isInsert 是否插入(0:插入,1:替换)
	 * @param isTop 是否置顶(true:是,false:否)
	 */
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop);
	
	/**
	 * 查询文章主体
	 * 
	 * @param channelId 频道编号
	 * @param sort 排序号
	 * @param isTop 是否置顶(true:是,false:否)
	 * @return Boolean true:存在,false:不存在
	 */
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop);

	/**
	 * 清除文章主体排序
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelId 频道编号
	 */
	public void clearArticleMainSort(List<Long> articleMainIds, Integer channelId);
	
	/**
	 * 新增文章信息
	 * 
	 * @param article 文章信息对象
	 * @param channelId 频道编号
	 * @param published 发布时间
	 * @return Long 文章主体编号
	 */
	public Long addArticle(Article article, Integer channelId, Date published);
	
	/**
	 * 修改文章信息
	 * 
	 * @param article 文章信息对象
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param published 发布时间
	 * @return Long 文章主体编号
	 */
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published, String userName);

	/**
	 * 文章与文章分类属性是否有关联
	 * 
	 * @param articleId 文章信息编号
	 * @param articleCategoryId 文章分类属性编号
	 * @return Boolean true:是,false:否
	 */
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId);

	/**
	 * 保存相关文章
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void saveRelation(Long articleId, Long[] relationArticleIds);
	
	/**
	 * 删除相关文章
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void deleteRelation(Long articleId, Long[] relationArticleIds);
	
	/**
	 * 相关文章向上移动一位
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void upRelation(Long articleId, Long[] relationArticleIds);

	/**
	 * 相关文章向下移动一位
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void downRelation(Long articleId, Long[] relationArticleIds);
	
	/**
	 * 查询相关文章集合
	 * 
	 * @param articleId 文章信息编号
	 * @return List 相关文章集合
	 */
	public List<Relation> findRelationByArticle(Long articleId);
	
	public List<Article> findPreReleaseArticles(Integer channelId, Integer limit);

	public List<Article> findReleaseArticlePage(Integer channelId,	Integer page, Integer row, Boolean top);

	public Article getArticle(Long id);

	public int getArticleCount(Integer channelId);

	public void publishArticle(Long id, String url);

	public void updatePreRelease(Integer channelId);
	
	/**
	 * 新增审核流程
	 * 
	 * @param channelId 频道编号
	 * @param reviewProcess 审核流程对象
	 * @return Long 审核流程编号
	 */
	public Long addReviewProcess(Integer channelId, ReviewProcess reviewProcess);
	
	/**
	 * 修改审核流程
	 * 
	 * @param reviewProcess 审核流程对象
	 * @return Long 审核流程编号
	 */
	public Long updReviewProcess(ReviewProcess reviewProcess);
	
	/**
	 * 删除审核流程
	 * 
	 * @param reviewProcessId 审核流程编号
	 */
	public void delReviewProcess(Long reviewProcessId);
	
	/**
	 * 审核流程上移一位
	 * 
	 * @param channelId 频道编号
	 * @param reviewProcessId 审核流程编号
	 */
	public void upReivewProcess(Integer channelId, Long reviewProcessId);
	
	/**
	 * 审核流程下移一位
	 * 
	 * @param channelId 频道编号
	 * @param reviewProcessId 审核流程编号
	 */
	public void downReviewProcess(Integer channelId, Long reviewProcessId);
	
	/**
	 * 查询审核流程
	 * 
	 * @param reviewProcessId 审核流程编号
	 * @return ReviewProcess 审核流程对象
	 */
	public ReviewProcess findReviewProcess(Long reviewProcessId);
	
	/**
	 * 查询频道下的所有审核流程
	 * 
	 * @param channelId 频道编号
	 * @return List 审核流程对象集合
	 */
	public List<ReviewProcess> findReviewProcessByChannel(Integer channelId);
	
	/**
	 * 查询频道下的审核流程第一个节点
	 * 
	 * @param channelId 频道编号
	 * @return ReviewProcess 审核流程对象
	 */
	public ReviewProcess findFirstReviewProcessByChannel(Integer channelId);
	
	/**
	 * 查询频道下的审核流程数
	 * 
	 * @param channelId 频道编号
	 * @return Long 个数
	 */
	public Long findReviewProcessCountByChannel(Integer channelId);
}
