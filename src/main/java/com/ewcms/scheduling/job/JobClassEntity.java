/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job;

/**
 * 执行定时工作任务实体类路径
 * 
 * @author 吴智俊
 */
public class JobClassEntity {
	public static final String JOB_CHANNEL = "com.ewcms.scheduling.job.channel.EwcmsExecutionChannelJob";
	public static final String JOB_LEADINGWINDOW = "com.ewcms.scheduling.job.leadingwindow.EwcmsExecutionLeadingWindowJob";
}
