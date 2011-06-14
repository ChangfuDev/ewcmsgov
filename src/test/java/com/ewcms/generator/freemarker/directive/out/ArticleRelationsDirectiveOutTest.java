/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Relation;

/**
 * ArticleRelationsDirectiveOut单元测试
 * 
 * @author wangwei
 */
public class ArticleRelationsDirectiveOutTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testRelatedIsNullOfLoopValue()throws Exception{
        ArticleRelationsDirectiveOut out = new ArticleRelationsDirectiveOut();
        List<Article> list = (List<Article>)out.loopValue(null, null, null);
        Assert.assertTrue(list.isEmpty());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testLoopValue()throws Exception{
        ArticleRelationsDirectiveOut out = new ArticleRelationsDirectiveOut();
        List<Article> list = (List<Article>)out.loopValue(initRelateds(), null, null);
        Assert.assertTrue(list.size() == 2);
    }
    
    @Test
    public void testRelatedIsEmptyOfConstructOut()throws Exception{
        ArticleRelationsDirectiveOut out = new ArticleRelationsDirectiveOut();
        String outValue = out.constructOut(null, null, null);
        Assert.assertNull(outValue);
        
        outValue = out.constructOut(new ArrayList<Relation>(),null, null);
        Assert.assertNull(outValue);
    }
    
    @Test
    public void testConstructOut()throws Exception{
        ArticleRelationsDirectiveOut out = new ArticleRelationsDirectiveOut();
        String outValue = out.constructOut(initRelateds(), null, null);
        String expected = "<ul>" +
        		"<li><a href=\"/index.html\" title=\"test\" target=\"_blank\">test</a></li>" +
        		"<li><a href=\"/index1.html\" title=\"test1\" target=\"_blank\">test1</a></li>" +
        		"</ul>";
        Assert.assertEquals(expected, outValue);
    }
    
    private List<Relation> initRelateds(){
        List<Relation> list = new ArrayList<Relation>();
        
        Relation r = new Relation();
        Article article = new Article();
        article.setUrl("/index.html");
        article.setTitle("test");
        r.setArticle(article);
        list.add(r);
        r = new Relation();
        article = new Article();
        article.setUrl("/index1.html");
        article.setTitle("test1");
        r.setArticle(article);
        list.add(r);
        
        return list;
    }
}
