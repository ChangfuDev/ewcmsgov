/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.crawler.fac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.job.crawler.service.EwcmsJobCrawlerServiceable;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

/**
 * 采集器定时任务Fac
 * 
 * @author 吴智俊
 */
@Service
public class EwcmsJobCrawlerFac implements EwcmsJobCrawlerFacable {
	@Autowired
	private EwcmsJobCrawlerServiceable ewcmsJobCrawlerService;

	@Override
	public EwcmsJobCrawler getScheduledJobCrawler(Integer jobId) {
		return ewcmsJobCrawlerService.getScheduledJobCrawler(jobId);
	}

	@Override
	public Integer saveOrUpdateJobCrawler(Long gatherId, PageDisplayVO vo) throws BaseException {
		return ewcmsJobCrawlerService.saveOrUpdateJobCrawler(gatherId, vo);
	}
}
