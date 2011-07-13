/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.generator.PublishException;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.uri.UriRule;

public class GeneratorBaseTest {

    @Test
    public void testCreateTempFile()throws Exception{
        GeneratorHtml html = new GeneratorHtml();
        File file = html.createTempFile();
        Assert.assertNotNull(file);
        Assert.assertTrue(file.getName().length()>32);
    }
    
    @Test
    public void testCompleteParameters()throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("o", "test");
        
        UriRule rule = new UriRule();
        rule.parse("${o}");
        
        GeneratorHtml html = new GeneratorHtml();
        html.completeParameters(map,rule);
        
        Assert.assertEquals(2, map.size());
        Assert.assertTrue(map.containsKey(GlobalVariable.URI_RULE.toString()));
        Assert.assertEquals("test", rule.getUri());
    }
    
    static class GeneratorHtml extends GeneratorBase{

        @Override
        public List<OutputResource> process(Template template, Site site,Channel channel) throws PublishException {
            return null;
        }
    }
}
