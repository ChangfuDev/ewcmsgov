/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.channel.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.model.AlqcJob;

/**
 * 频道定时任务
 * 
 * <ul>
 * <li>channel:频道对象</li>
 * <li>subChannel:是否应用于子频道</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "job_info_channel")
@PrimaryKeyJoinColumn(name = "info_id")
public class EwcmsJobChannel extends AlqcJob {

	private static final long serialVersionUID = -4373031603153928098L;
	
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "channel_id")
	private Channel channel;
    @Column(name = "sub_channel", nullable = false)
    private Boolean subChannel = true;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Boolean getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(Boolean subChannel) {
		this.subChannel = subChannel;
	}
}
