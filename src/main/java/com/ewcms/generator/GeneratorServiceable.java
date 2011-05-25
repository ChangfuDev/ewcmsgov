/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator;

import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author wangwei
 */
public interface GeneratorServiceable {

    void generator(int channelId)throws ReleaseException;
    
    void reGenerator(int channelId)throws ReleaseException;

    void generatorDebug(int channelId)throws ReleaseException;
    
    void generatorResourceSingle(int id)throws ReleaseException;
    
    void generatorResourceTPLSingle(int id)throws ReleaseException;
}
