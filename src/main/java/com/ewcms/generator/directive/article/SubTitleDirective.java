/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.article;

import com.ewcms.core.document.model.Article;
import com.ewcms.core.document.model.ArticleRmc;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service("direcitve.article.subTitle")
public class SubTitleDirective extends TitleDirective {

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        Article article = this.getArticle(articleRmc);
        return constructOutValue(article.getSubTitle(), article.getSubTitleStyle());
    }
}
