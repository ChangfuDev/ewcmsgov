/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.service;

import java.io.File;
import java.io.IOException;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.publication.service.ResourcePublishServiceable;

/**
 * 上传文件操作接口
 * 
 * @author 吴智俊
 */
public interface ResourceServiceable extends ResourcePublishServiceable {

    /**
     * 新增上传文件
     *
     * @param uploadFile
     * @return
     */
    public Resource addResource(File file,String fileName,ResourceType type)throws IOException;

    /**
     * 删除上传文件
     *
     * @param uploadFileId
     */
    public void delResource(Integer id);

    /**
     * 查询上传文件
     *
     * @param uploadFileId
     * @return
     */
    public Resource getResource(Integer id);

    /**
     * 更新资源信息
     * 
     * @param id
     * @param title
     * @param description
     */
    public Resource updResourceInfo(Integer id,String title,String description);
}
