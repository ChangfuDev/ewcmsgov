/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview;

import java.io.OutputStream;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.PublishException;

/**
 * 模板预览接口
 * 
 * @author wangwei
 */
public interface TemplatePreviewable {

    /**
     * 模板预览
     * 
     * @param out       
     *          输出数据流
     * @param site       
     *          站点
     * @param channel       
     *          频道
     * @param template
     *          模板
     * @param mock
     *          是否模拟 
     * @throws PublishException
     */
    public void view(OutputStream out,Site site,Channel channel,Template template,Boolean mock)throws PublishException;
       
    /**
     * 文章预览
     * 
     * @param out       
     *          输出数据流
     * @param site       
     *          站点
     * @param channel       
     *          频道
     * @param template
     *          模板
     * @param id
     *          文章编号
     * @param pageNumber
     *          页数 
     * @throws PublishException
     */
    public void viewArticle(OutputStream out,Site site,Channel channel,Template template,Long id,int pageNumber)throws PublishException;
}
