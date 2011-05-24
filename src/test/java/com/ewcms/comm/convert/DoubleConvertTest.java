/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.Convertable;
import com.ewcms.common.convert.DoubleConvert;

/**
 * 测试Double数据类型转换
 * 
 * @author 王伟
 */
public class DoubleConvertTest {

    private Convertable handler;
    
    @Before
    public void setUp(){
        this.handler = new DoubleConvert();
    }
    
    @Test
    public void testParseSuccess() throws Exception{

        String test = "200";
        Double testDouble = (Double) handler.parse(test);
        assertEquals(200, testDouble.doubleValue(), 0);

        test = "0.23";
        testDouble = (Double) handler.parse(test);
        assertEquals(0.23, testDouble, 0);
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception{
        String test = "200.2.2";
        handler.parse(test);
    }

    public void testParseString() throws Exception{
        
        assertEquals(Double.valueOf((double)200),"200");
    }
}
