/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.List;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.crawler.model.Gather;
import com.ewcms.web.QueryBaseAction;

public class GatherQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 646183126823561961L;

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(Gather.class).setPage(page).setRow(rows).orderAsc("id");
    	
    	Long id = getParameterValue(Long.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) query.eq("id", id);
    	        
    	String name = getParameterValue(String.class, "name", "");
    	if (isStringNotEmpty(name)) query.likeAnywhere("name", name);
    	
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(Gather.class).setPage(page).setRow(rows).orderAsc("id");
    	
        List<Long> ids = getIds(Long.class);
        query.in("id", ids);
        
        return query.queryResult();    
	}

}
