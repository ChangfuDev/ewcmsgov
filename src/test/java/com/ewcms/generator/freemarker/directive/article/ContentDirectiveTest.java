/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.article;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.article.ContentDirective;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public class ContentDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(ContentDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("article_content", new ContentDirective());
    }

    @Test
    public void testExecutePage() throws Exception {
        Template template = cfg.getTemplate("www/article/content.html");

        Map params= new HashMap();
        params.put("arti", getArticle());
        PageParam pageParam = new PageParam();
        pageParam.setPage(2);
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        String value = this.process(template, params);
        
        log.info(value);

        Assert.assertTrue(value.indexOf("test single page1") == -1);
        Assert.assertTrue(value.indexOf("test single page2") != -1);
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/article/content.html");

        Map params= new HashMap();
        params.put("arti", getArticle());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("test single page0") != -1);
        Assert.assertTrue(value.indexOf("test single page1") == -1);
    }

    private Article getArticle() {
        Article arti = new Article();

        List<Content> list = new ArrayList<Content>();
        for (int i = 0; i < 3; ++i) {
            Content content = new Content();
            content.setDetail("test single page" + String.valueOf(i));
            content.setPage(i + 1);
            list.add(content);
        }
        arti.setContents(list);
        
        return arti;
    }
}
