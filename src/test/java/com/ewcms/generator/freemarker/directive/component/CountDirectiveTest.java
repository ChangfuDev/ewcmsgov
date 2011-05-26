/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.component;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.component.CountDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author wangwei
 */
public class CountDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(CountDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("component_count", new CountDirective());
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/component/count.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("browse_count_element_id") != -1);
        Assert.assertTrue(value.indexOf("article_id=20") != -1);
        Assert.assertTrue(value.indexOf("channel_id=10") != -1);
        Assert.assertTrue(value.indexOf("callback=browse_count_callback") != -1);
    }

    @Test
    public void testExecuteElementId() throws Exception {
        Template template = cfg.getTemplate("www/component/count_element.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("test_browse_element_id") != -1);
        Assert.assertTrue(value.indexOf("article_id=20") != -1);
        Assert.assertTrue(value.indexOf("channel_id=10") != -1);
        Assert.assertTrue(value.indexOf("callback=browse_count_callback") != -1);
    }

    @Test
    public void testExecuteCallBack() throws Exception {
        Template template = cfg.getTemplate("www/component/count_callback.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("test_browse_element_id") == -1);
        Assert.assertTrue(value.indexOf("article_id=20") != -1);
        Assert.assertTrue(value.indexOf("channel_id=10") != -1);
        Assert.assertTrue(value.indexOf("callback=test_browse_callback") != -1);
    }

    private Article getArticle() {
        Article arti = new Article();

//        arti.setId(20);
//        Channel channel = new Channel();
//        channel.setId(10);
//        arti.setChannel(channel);

        return arti;
    }
}
