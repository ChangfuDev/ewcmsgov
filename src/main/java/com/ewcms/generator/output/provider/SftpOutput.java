/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.provider;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;

/**
 * 发布指定的资源到sftp服务器上
 * 
 * @author wangwei
 */
public class SftpOutput extends OutputBase {

    private static final Logger logger = LoggerFactory.getLogger(SftpOutput.class);
    
    @Override
    protected FileObject getTargetRoot(SiteServer server,FileSystemManager manager)throws FileSystemException{
        
        FileSystemOptions opts = new FileSystemOptions();
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
        
        StringBuilder builder = new StringBuilder();
        builder.append("sftp://");
        builder.append(server.getUser()).append(":").append(server.getPassword());
        builder.append("@").append(server.getHostName()).append(":").append(server.getPort());
        builder.append(server.getPath());
        
        String address = builder.toString();
        logger.debug("uri is sftp://{}:xxxx@{}:{}{}",new String[]{server.getUser(),server.getHostName(),server.getPort(),server.getPath()});
        
        return manager.resolveFile(address, opts);
    }
}
