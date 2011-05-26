/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * 执行定时工作任务抽象类
 * 
 * @author 吴智俊
 */
public abstract class BaseExecutionJob implements Job {
    private static final Log log = LogFactory.getLog(BaseExecutionJob.class);
    
    public static final String SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT = "applicationContext";
    public static final String JOB_DATA_KEY_DETAILS_ID = "jobDetailsID";

    protected ApplicationContext applicationContext;
    protected JobExecutionContext jobContext;
    protected SchedulerContext schedulerContext;
    

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	log.info("定时器开始...");
        try {
            this.jobContext = context;
            this.schedulerContext = jobContext.getScheduler().getContext();

            this.applicationContext = (ApplicationContext) schedulerContext.get(SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT);
            
            jobExecute(context);
        } catch (JobExecutionException e) {
        	throw new JobExecutionException(e);
        } catch (SchedulerException e) {
            throw new JobExecutionException(e);
        } 
        log.info("定时器结束.");
    }
    
    protected void clear() {
        jobContext = null;
        schedulerContext = null;
        jobClear();
    }

    protected abstract void jobExecute(JobExecutionContext context) throws JobExecutionException;

    protected abstract void jobClear();
}
