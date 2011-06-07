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
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.ElementDirective;

/**
 *
 * @author wangwei
 */

public abstract class ArticleElementDirective extends ElementDirective<Article>{

    @Override
    protected String defaultVariable() {
        return DirectiveVariable.Article.toString();
    }

    protected Article getArticle(Article article){
        return article;
    }
}
