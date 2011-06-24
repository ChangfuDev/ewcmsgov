/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.service.ChannelLoaderServiceable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 频道列表标签
 * 
 * @author wangwei
 */
public class ChannelListDirective implements TemplateDirectiveModel {
    private static final Logger logger = LoggerFactory.getLogger(ChannelListDirective.class);
    
    private static final int DEFAULT_ROW = -1;
    
    private static final String VALUE_PARAM_NAME = "value";
    private static final String CHANNEL_PARAM_NAME = "channel";
    private static final String ROW_PARAM_NAME = "row";
    private static final String NAME_PARAM_NAME = "name";
    private static final String CHILD_PARAM_NAME="child";

    private String valueParam = VALUE_PARAM_NAME;
    private String channelParam = CHANNEL_PARAM_NAME;
    private String rowParam = ROW_PARAM_NAME;
    private String nameParam = NAME_PARAM_NAME;
    private String childParam = CHILD_PARAM_NAME;
    
    private ChannelLoaderServiceable channelLoaderService;
    
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
            boolean child = getChildValue(params);
            int row = getRowValue(params);
            int siteId = getSiteIdValue(env);
            
            List<Channel> channels = getChannelListByChannel(env,params, siteId, child);
            if(EmptyUtil.isCollectionEmpty(channels)){
                channels = getChannelListByValue(env,params, siteId, child);
            }
            
            row = (row == DEFAULT_ROW) ? channels.size() : row;
            if(row < channels.size()){
                channels = channels.subList(0, row);
            }
            
            if (EmptyUtil.isArrayNotEmpty(loopVars)) {
                loopVars[0] = env.getObjectWrapper().wrap(channels);
                if(EmptyUtil.isNull(body)){
                    logger.warn("Body is null");
                }else{
                    body.render(env.getOut());    
                }
            } else {
                String name = getNameValue(params);
                Writer writer = env.getOut();
                for (int i = 0 ; i < channels.size(); i++) {
                    Channel channel = channels.get(i);
                    FreemarkerUtil.setVariable(env, name, channel);
                    FreemarkerUtil.setVariable(env, GlobalVariable.INDEX.toString(), i + 1);
                    body.render(writer);
                    FreemarkerUtil.removeVariable(env, GlobalVariable.INDEX.toString());
                    FreemarkerUtil.removeVariable(env, name);
                }
                writer.flush();
            }
    }

    /**
     * 得到当前站点
     *
     * @param env Environment
     * @return
     * @throws TemplateException
     */
    private Integer getSiteIdValue(Environment env) throws TemplateException {
        String current = GlobalVariable.SITE.toString();
        Site site = (Site) FreemarkerUtil.getBean(env, current);
        if(EmptyUtil.isNull(site)){
            logger.error("Site is null in freemarker variable");
            throw new TemplateModelException("Site is null in freemarker variable");
        }
        logger.debug("Site is {}",site);
        return site.getId();
    }

    /**
     * 通过“value”参数值得到显示频道集合
     * 
     * @param env
     *          Freemarker环境
     * @param params
     *          标签参数集合
     * @param siteId 
     *          站点编号
     * @param child 
     *          显示子频道
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private List<Channel> getChannelListByValue(Environment env,Map params,int siteId,boolean child)throws TemplateException{
        return getChannelList(env,params,siteId,valueParam,child);
    }
    
    /**
     * 通过“channel”参数值得到显示频道集合
     * 
     * @param env
     *          Freemarker环境
     * @param params
     *          标签参数集合
     * @param siteId 
     *          站点编号
     * @param child 
     *          显示子频道
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private List<Channel> getChannelListByChannel(Environment env,Map params,int siteId,boolean child)throws TemplateException{
        return getChannelList(env,params,siteId,channelParam,child);
    }
    
    /**
     * 得到显示频道集合
     * 
     * @param env
     *          Freemarker环境
     * @param params
     *          标签参数集合
     * @param name   
     *          标签参数名称
     * @param child 
     *          显示子频道
     * @return
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    private List<Channel> getChannelList(Environment env,Map params,int siteId,String name,boolean child)throws TemplateException{
        
        Channel channel = (Channel) FreemarkerUtil.getBean(params, name);
        if (EmptyUtil.isNotNull(channel)) {
            return loadingChannelWithPublicenable(siteId, channel.getId(),child);
        }

        Integer channelId = FreemarkerUtil.getInteger(params, name);
        if (EmptyUtil.isNotNull(channelId)) {
            return loadingChannelWithPublicenable(siteId, channelId,child);
        }

        String value = FreemarkerUtil.getString(params, name);
        if (EmptyUtil.isStringNotEmpty(value)) {
            return loadingChannelWithPublicenable(env,siteId,value,child);
        }

        Object[] values = (Object[]) FreemarkerUtil.getSequence(params, name);
        if (EmptyUtil.isArrayNotEmpty(values)) {
            return loadingArrayChannelWithPublicenable(env,siteId,values,child);
        }

        channel = (Channel) FreemarkerUtil.getBean(env, GlobalVariable.CHANNEL.toString());
        if(EmptyUtil.isNotNull(channel)){
            return loadingChannelWithPublicenable(siteId, channel.getId(),child);    
        }
        return new ArrayList<Channel>();
    }
    
    /**
     * 判断频道是否发布
     * 
     * @param siteId
     *             站点编号
     * @param channelId 
     *             频道编号
     * @return
     * @throws TemplateException
     */
    private boolean isPublicenable(int siteId,int channelId) throws TemplateException {
        Channel channel = channelLoaderService.getChannel(siteId,channelId);
        if (EmptyUtil.isNull(channel)) {
            logger.error("Channel's id is {},it's not exist in site's({}).",channelId,siteId);
            throw new TemplateModelException("Channl's id is " + channelId + ",it's not exist.");
        }
        return channel.getPublicenable();
    }
    
    /**
     * 通过频道编号加载已经发布的频道列表
     * 
     * @param siteId 站点编号
     * @param channelId 频道编号
     * @param child 显示子站点
     * @return
     * @throws TemplateException
     */
    List<Channel> loadingChannelWithPublicenable(int siteId,int channelId,boolean child)throws TemplateException{
        
        boolean pub = isPublicenable(siteId,channelId);
        
        if(!pub){
            return new ArrayList<Channel>();
        }
        
        List<Channel> list = new ArrayList<Channel>();
        if(child){
            List<Channel> children = channelLoaderService.getChannelChildren(channelId);
            for(Channel c: children){
                if(c.getPublicenable()){
                    list.add(c);
                }
            }
        }else{
            list.add(channelLoaderService.getChannel(siteId,channelId));
        }
        
        return list;
    }
    
    /**
     * 通过频道url或变量名称加载已经发布的频道列表
     * 
     * @param env 
     *          Freemarker环境
     * @param siteId 
     *          站点编号
     * @param value 
     *          url或变量名称
     * @param child 
     *          显示子站点
     * @return
     * @throws TemplateException
     */
    private List<Channel> loadingChannelWithPublicenable(Environment env, int siteId,String value, boolean child)throws TemplateException{
        Channel channel = (Channel)FreemarkerUtil.getBean(env, value);
        if(EmptyUtil.isNotNull(channel)){
            logger.debug("Channel is {}",channel.toString());
            return loadingChannelWithPublicenable(siteId, channel.getId(),child);
        }
        channel = channelLoaderService.getChannelByUrlOrPath(siteId, value);
        if(EmptyUtil.isNotNull(channel)){
            logger.debug("Channel is {}",channel.toString());
            return loadingChannelWithPublicenable(siteId, channel.getId(),child);
        }
        throw new TemplateModelException(value + "Url or variable " + value + " had not exist");
    }
    
    /**
     * 通过频道数组加载已经发布的频道列表
     * <br>
     * 数组内可以是频道编号，频道地址，变量名称。
     * 
     * @param env Freemarker环境
     * @param siteId 站点编号
     * @param values 频道集合
     * @param child 显示子站点
     * @return
     * @throws TemplateException
     */
    private List<Channel> loadingArrayChannelWithPublicenable(Environment env,int siteId,Object[] values,boolean child)throws TemplateException{
        
        List<Channel> channels = new ArrayList<Channel>();
        for (Object value : values) {
            if (value instanceof Integer) {
                logger.debug("Channel's id is {}",value);
                channels.addAll(loadingChannelWithPublicenable(siteId,(Integer) value,child));
            }else if (value instanceof String) {
                logger.debug("Channel's url or variable is {}",value);
                channels.addAll(loadingChannelWithPublicenable(env,siteId,(String)value,child));
            }else{
                logger.debug("Channel's value type is{}",value.getClass().getName());
                throw new TemplateModelException("Channel array are only int and string");
            }
        }
        return channels;
    }
    
    @SuppressWarnings("rawtypes")
    private int getRowValue(Map params) throws TemplateModelException {
        Integer rows = FreemarkerUtil.getInteger(params, rowParam);
        return rows == null ? DEFAULT_ROW : rows;
    }

    @SuppressWarnings("rawtypes")
    private String getNameValue(Map params) throws TemplateModelException {
        String value = FreemarkerUtil.getString(params, nameParam);
        return value == null ? GlobalVariable.CHANNEL.toString() : value;
    }

     @SuppressWarnings("rawtypes")
    private boolean getChildValue(Map params) throws TemplateModelException {
        Boolean value = FreemarkerUtil.getBoolean(params, childParam);
        return value == null ? false : value;
    }

    public void setValueParam(String valueParam) {
        this.valueParam = valueParam;
    }

    public void setChannelParam(String channelParam) {
        this.channelParam = channelParam;
    }

    public void setRowParam(String rowParam) {
        this.rowParam = rowParam;
    }

    public void setNameParam(String nameParam) {
        this.nameParam = nameParam;
    }

    public void setChildParam(String childParam) {
        this.childParam = childParam;
    }

    public void setChannelLoaderService(ChannelLoaderServiceable service) {
        this.channelLoaderService = service;
    }
}
