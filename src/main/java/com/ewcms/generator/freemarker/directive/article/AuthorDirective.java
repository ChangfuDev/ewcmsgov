/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.article;

/**
 *文章作者标签
 *
 * @deprecated
 * @author wangwei
 */
public class AuthorDirective extends ArticlePropertyDirective {
    
    @Override
    protected String getPropertyName() {
        return "author";
    }
    
}
