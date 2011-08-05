/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.channel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;

/**
 * 频道定时任务DAO
 * 
 * @author 吴智俊
 */
@Repository()
public class EwcmsJobChannelDAO extends JpaDAO<Integer, EwcmsJobChannel> {
	@SuppressWarnings("unchecked")
	public EwcmsJobChannel findJobChannelByChannelId(Integer channelId) {
		String hql = "Select o From EwcmsJobChannel o Inner Join o.channel c Where c.id=?";
		List<EwcmsJobChannel> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Channel> getChannelChildren(final Integer parentId) {
		Object res = this.getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				if (parentId == null)
					return null;

				String hql = "From Channel o Where o.parent.id=? Order By o.id";
				Query query = em.createQuery(hql);
				query.setParameter(1, parentId);
				return query.getResultList();

			}
		});
		return (List<Channel>) res;
	}

}
