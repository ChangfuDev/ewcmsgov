package com.ewcms.plugin.report.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class ChartReportQueryAction extends QueryBaseAction{

	private static final long serialVersionUID = -1268766311745288459L;

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(ChartReport.class).setPage(page).setRow(rows).orderDesc("id");
    	
		Long id = getParameterValue(Long.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) query.eq("id", id);
    	
        String name = getParameterValue(String.class, "name", "");
        if (isStringNotEmpty(name)) query.likeAnywhere("name", name);
        
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query =  queryFactory.createEntityQuery(ChartReport.class).setPage(page).setRow(rows).orderAsc("id");
        
        query.in("id", getIds(Long.class));
        
        return query.queryResult();    
	}

}
