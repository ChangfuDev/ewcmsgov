/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.uri;

import java.util.Map;

import com.ewcms.generator.ReleaseException;

/**
 * 资源发布地址规则接口
 * 
 * @author wangwei
 */
public interface UriRuleable {
    
    /**
     * 解析uri模板
     * 
     * @param patter uri模板
     * @throws ReleaseException
     */
    void parse(String patter)throws ReleaseException;
    
    /**
     * 设置参数集合
     * 
     * @param parameters
     */
    void setParameters(Map<String,Object> parameters);
    
    /**
     * 放入参数到参数集合中,如果参数存在则覆盖参数
     * 
     * @param parameter 参数名
     * @param value 参数值
     */
     void putParameter(String parameter,Object value);
    
    /**
     * 得到通一资源地址
     * 
     * @return
     * @throws ReleaseException
     */
     String getUri()throws ReleaseException;
    
}
