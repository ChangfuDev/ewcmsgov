/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.ObjectBean;
import com.ewcms.generator.PublishException;
import com.ewcms.generator.freemarker.GlobalVariable;

/**
 * UrlRule单元测试
 * 
 * @author wangwei
 */
public class UriRuleTest {
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    public void testNoneVariableParseVariables()throws Exception{
        String patter = "home/index.html";
        
        UriRule rule = new UriRule();
        List<String[]> variables = rule.parseVariables(patter);
        Assert.assertTrue(variables.isEmpty());
    }
    
    @Test
    public void testPatterErrorParseVariables(){
        
        try{
            String patter = "home/${index.html";
            UriRule rule = new UriRule();
            rule.parseVariables(patter);
            Assert.fail();
        }catch(PublishException e){
            
        }
    }
    
    @Test
    public void testParseVariables()throws Exception{
        String patter = "news/cn/${now?yyyy-MM-dd}/${a.id}.html";
        
        UriRule rule = new UriRule();
        List<String[]> variables = rule.parseVariables(patter);
        Assert.assertEquals(2, variables.size());
        Assert.assertEquals("now", variables.get(0)[0]);
        Assert.assertEquals("yyyy-MM-dd", variables.get(0)[1]);
        Assert.assertEquals("a.id", variables.get(1)[0]);
        Assert.assertNull(variables.get(1)[1]);
    }
    
    @Test
    public void testParameterIsNoneGetVariableValue()throws Exception{
        Map<String,Object> parameters =new HashMap<String,Object>();
        
        try{
            UriRule rule = new UriRule();
            rule.getVariableValue("a.id", parameters);
            Assert.fail();
        }catch(PublishException e){
        }
    }
    
    @Test
    public void testGetVariableValue()throws Exception{
       Map<String,Object> parameters = new HashMap<String,Object>();
       parameters.put("o", initObjectBean()); 
       UriRule rule = new UriRule();
       Object value =rule.getVariableValue("o.title", parameters);        
       Assert.assertEquals("root", value);
       
       value =rule.getVariableValue("o.parent.title", parameters); 
       Assert.assertEquals("child", value);
    }
    
    @Test
    public void testStringTypeFormatValue(){
        
        UriRule rule = new UriRule();
        String value = rule.formatValue("test", null);
        Assert.assertEquals("test",value);
    }
    
    @Test
    public void testDateTypeFormatValue(){
        
        UriRule rule = new UriRule();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 0, 1);
        
        String value = rule.formatValue(new Date(calendar.getTimeInMillis()), null);
        Assert.assertEquals("2011-01-01", value);
        
        value = rule.formatValue(new Date(calendar.getTimeInMillis()), "yyyy年MM月dd日");
        Assert.assertEquals("2011年01月01日", value);
    }
    
    @Test
    public void testNumberTypeFormatValue(){
        
        UriRule rule = new UriRule();
        
        Integer number = Integer.valueOf(1099);
        String value = rule.formatValue(number, null);
        Assert.assertEquals("1099", value);
        
        Float f = 2.55f;
        value = rule.formatValue(f, "#.0");
        Assert.assertEquals("2.5", value);
    }
    
    @Test
    public void testGetUri()throws Exception{
        String patter = "news/cn/${o.id}.html";
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("o", initObjectBean()); 
        UriRule rule = new UriRule();
        rule.parse(patter);
        rule.setParameters(parameters);
        String uri = rule.getUri();
        Assert.assertEquals("news/cn/1.html", uri);
    }
    
    @Test
    public void testInnerVariableGetUri()throws Exception{
        String patter = "news/cn/${now?yyyy-MM-dd}/${c.id}_${p}.html";
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put(GlobalVariable.CHANNEL.toString(), initObjectBean()); 
        parameters.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(1));
        
        UriRule rule = new UriRule();
        rule.parse(patter);
        rule.setParameters(parameters);
        String uri = rule.getUri();
        String expected ="news/cn/"+ dateFormat.format(new Date()) + "/1_1.html";
        Assert.assertEquals(expected, uri);
    }
    
    private ObjectBean initObjectBean(){
        ObjectBean bean = new ObjectBean();
        bean.setId(1);
        bean.setTitle("root");
        
        ObjectBean child = new ObjectBean();
        child.setTitle("child");
        bean.setParent(child);
        
        return bean;
    }
    
    
}
