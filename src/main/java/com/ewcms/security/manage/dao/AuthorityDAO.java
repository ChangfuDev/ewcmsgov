/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.jict.org
 */
package com.ewcms.security.manage.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.security.manage.model.Authority;

/**
 * 通用权限数据操作
 * 
 * @author wangwei
 */
@Repository
public class AuthorityDAO extends JpaDAO<String,Authority> implements AuthorityDAOable{
    
}
