/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.channel;

import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.ElementDirective;

/**
 * 频道元素显示
 *
 * @author wangwei
 */
public abstract class ChannelElementDirective extends ElementDirective<Channel>{

    @Override
    protected String defaultVariable() {
        return DirectiveVariable.Channel.toString();
    }
}
