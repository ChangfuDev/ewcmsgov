package com.ewcms.security.manage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.common.jpa.query.EntityPageQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.web.QueryBaseAction;

@Controller("security.authority.query")
public class AuthorityQueryAction extends QueryBaseAction{

    @Autowired
    private QueryFactory queryFactory;
    
    @Override
    protected PageQueryable constructQuery(Order order) {
        
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Authority.class);
        String name =  getParameterValue(String.class,"name");
        if(this.isNotEmpty(name)) query.likeAnywhere("name", name);
        simpleEntityOrder(query, order);
        
        return query;
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        return null;
    }
    
    public void setQueryFactory(QueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

}
