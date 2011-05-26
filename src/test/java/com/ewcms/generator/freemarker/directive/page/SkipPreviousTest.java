/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.page;

import org.junit.Test;
import org.junit.Assert;

import com.ewcms.generator.freemarker.directive.page.Page;
import com.ewcms.generator.freemarker.directive.page.PageParam;
import com.ewcms.generator.freemarker.directive.page.SkipPrevious;

/**
 *
 * @author wangwei
 */
public class SkipPreviousTest {

    @Test
    public void testSkip() {
        PageParam pageParam = new PageParam();
        pageParam.setCount(10);
        pageParam.setPage(2);
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");

        SkipPrevious skip = new SkipPrevious();
        Page page = skip.skip(pageParam);
        Assert.assertEquals(page.getLabel(), "上一页");
        Assert.assertEquals(page.getUrl(),"http://test.com/dddd_1.html");
        Assert.assertTrue(page.isEnabled());

        pageParam.setPage(0);
        page = skip.skip(pageParam);
        Assert.assertFalse(page.isEnabled());
    }
}
