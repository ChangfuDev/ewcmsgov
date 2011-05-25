/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Site;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 包含标签<br/>
 * 
 * <@include path>
 *
 * @author wangwei
 */
@Service("direcitve.include")
public class IncludeDirective implements TemplateDirectiveModel {

    private final static String PARAM_NAME_PATH = "path";
    private final static String PARAM_NAME_PARSE = "parse";
    
    @Override
    public void execute(final Environment env, final Map params, final TemplateModel[] loopVars, final TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            Site site = getCurrentSiteVariable(env);
            boolean parse = getParseParam(params);
            String path = getPathParam(params);
            String realPath = getUniquePath(site,path);
            env.include(realPath, env.getConfiguration().getDefaultEncoding(), parse);
        } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch(Exception e){
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    private Site getCurrentSiteVariable(final Environment env) throws TemplateModelException {
        Site site = (Site) DirectiveUtil.getBean(env, DirectiveVariable.CurrentSite.toString());
        Assert.notNull(site, "site is null");
        return site;
    }

    private String getPathParam(final Map params) throws TemplateModelException, DirectiveException {
        String path = DirectiveUtil.getString(params, PARAM_NAME_PATH);
        if (EmptyUtil.isNull(path)) {
            throw new DirectiveException("需要模板路径");
        }
        return path;
    }

    String getUniquePath(Site site, String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return String.format("/%d/%s",site.getId(),path);
    }

    private boolean getParseParam(final Map params) throws TemplateModelException {
        Boolean enabled = DirectiveUtil.getBoolean(params, PARAM_NAME_PARSE);
        return EmptyUtil.isNull(enabled) ? true : enabled;
    }
}
