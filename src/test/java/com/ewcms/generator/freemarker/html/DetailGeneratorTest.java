/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.freemarker.directive.ArticleDirective;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.service.ArticlePublishServiceable;

import freemarker.template.Configuration;

public class DetailGeneratorTest extends GeneratorHtmlTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("article", new ArticleDirective());
    }
    
    @Test
    public void testDetailTemplate()throws Exception{
        ArticlePublishServiceable service = mock(ArticlePublishServiceable.class);
        when(service.findReleaseArticles(any(Integer.class),any(Integer.class))).thenReturn(initArtilces());
        
        DetailGenerator generator = new DetailGenerator(cfg,service);
        List<OutputResource> resources = generator.process(initTemplate(getTemplatePath("detail.html")),initSite(),initChannel());
        Assert.assertEquals(1, resources.size());
        OutputResource resource = resources.get(0);
        Assert.assertEquals(4, resource.getChildren().size());
        assertEquals("document/2011-01-01/2_0.html","1page",resource.getChildren().get(0));
        assertEquals("document/2011-01-01/2_3.html","4page",resource.getChildren().get(3));
    }
    
    private void assertEquals(String uri,String content,OutputResource resource)throws Exception{
        Assert.assertEquals(uri, resource.getUri());
        String c =getContent(resource.getPath());
        c = StringUtils.deleteWhitespace(c);
        Assert.assertEquals(content, c);
    }
    
    private List<Article> initArtilces(){
        List<Article> articles = new ArrayList<Article>();
        
        Article  article = new Article();
        article.setContentTotal(4);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 0, 1);
        article.setId(2l);
        article.setCreateTime(new Date(calendar.getTimeInMillis()));
        
        List<Content> contents = new ArrayList<Content>();
        Content content = new Content();
        content.setDetail("1page");
        contents.add(content);
        content = new Content();
        content.setDetail("2page");
        contents.add(content);
        content = new Content();
        content.setDetail("3page");
        contents.add(content);
        content = new Content();
        content.setDetail("4page");
        contents.add(content);
        
        article.setContents(contents);
        articles.add(article);
        return articles;
    }

}
