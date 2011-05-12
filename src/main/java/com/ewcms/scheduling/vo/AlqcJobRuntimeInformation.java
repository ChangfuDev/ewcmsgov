/**
 * 创建日期 2009-4-12
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 调度器任务工作状态
 *
 * <ul>
 * <li>state:状态
 * <li>startTime:开始时间
 * <li>endTime:结束时间
 * <li>previousFireTime:上一次触发时间
 * <li>nextFireTime:下一次触发时间
 * </ul>
 *
 * @author 吴智俊
 */
public class AlqcJobRuntimeInformation implements Serializable {

    private static final long serialVersionUID = -3971621420142839444L;
    public static final String STATE_UNKNOWN = "已执行完";
    public static final String STATE_NORMAL = "正常";
    public static final String STATE_EXECUTING = "正在执行";
    public static final String STATE_PAUSED = "暂停";
    public static final String STATE_COMPLETE = "完成";
    public static final String STATE_ERROR = "错误";
    private String state;
    private Date startTime;
    private Date endTime;
    private Date previousFireTime;
    private Date nextFireTime;

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
