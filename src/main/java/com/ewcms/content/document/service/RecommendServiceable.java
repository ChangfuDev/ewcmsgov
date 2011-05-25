/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

/**
 * 推荐文章操作接口
 *
 * @author 吴智俊
 */
public interface RecommendServiceable {
	/**
	 * 保存推荐文章
	 * @param articleRmcId
	 * @param recommendArticleIds
	 */
	public void saveRecommend(Integer articleRmcId, Integer[] recommendArticleIds);
	
	/**
	 * 删除推荐文章
	 * 
	 * @param articleRmcId
	 * @param recommendArticleIds
	 */
	public void deleteRecommend(Integer articleRmcId, Integer[] recommendArticleIds);
	
	/**
	 * 推荐文章向上移动一位
	 * 
	 * @param articleRmcId
	 * @param recommendArticleIds
	 */
	public void upRecommend(Integer articleRmcId, Integer[] recommendArticleIds);

	/**
	 * 推荐文章向下移动一位
	 * 
	 * @param articleRmcId
	 * @param recommendArticleIds
	 */
	public void downRecommend(Integer articleRmcId, Integer[] recommendArticleIds);
}
